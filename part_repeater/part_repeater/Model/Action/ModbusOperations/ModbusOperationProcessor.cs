namespace part_repeater.Model.Action.ModbusOperations
{
    using part_repeater.Model;
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.IO.Ports;
    using System.Runtime.CompilerServices;
    using System.Threading;

    public class ModbusOperationProcessor
    {
        private Thread processThread;
        private Queue<ModbusOperationDelegate> queue = new Queue<ModbusOperationDelegate>();
        private SerialPort serialPort;
        private CancellationTokenSource cts = new CancellationTokenSource();
        private Random r = new Random();
        private IStationDataModel model;

        public ModbusOperationProcessor(SerialPort serialPort, IStationDataModel model)
        {
            this.serialPort = serialPort;
            this.model = model;
        }

        public void Add(ModbusOperationDelegate operation)
        {
            this.queue.Enqueue(operation);
        }

        public void Insert(ModbusOperationDelegate operation)
        {
            ModbusOperationDelegate[] delegateArray = this.queue.ToArray();
            this.queue.Clear();
            this.queue.Enqueue(operation);
            foreach (ModbusOperationDelegate delegate2 in delegateArray)
            {
                this.queue.Enqueue(delegate2);
            }
        }

        private void Process()
        {
            while (true)
            {
                ModbusOperationDelegate delegate2;
                CancellationToken token = this.cts.Token;
                if (token.IsCancellationRequested)
                {
                    return;
                }
                lock (this.queue)
                {
                    if (this.queue.Count == 0)
                    {
                        Thread.Sleep(0x3e8);
                        continue;
                    }
                    delegate2 = this.queue.Dequeue();
                }
                this.TryOperate(delegate2);
            }
        }

        public void Restart()
        {
            this.queue.Clear();
        }

        public void Restart(string portName)
        {
            if ((this.serialPort != null) && (this.serialPort.PortName == portName))
            {
                this.queue.Clear();
            }
        }

        public void StartProcess()
        {
            this.cts = new CancellationTokenSource();
            ThreadStart start = new ThreadStart(this.Process);
            this.processThread = new Thread(start);
            this.processThread.Start();
        }

        public void StopProcess()
        {
            this.cts.Cancel();
            this.queue.Clear();
        }

        public void StopProcess(string portName)
        {
            if ((this.serialPort != null) && (this.serialPort.PortName == portName))
            {
                this.StopProcess();
            }
        }

        private void TryOperate(ModbusOperationDelegate operate)
        {
            try
            {
                lock (this.serialPort)
                {
                    while (true)
                    {
                        if (!this.serialPort.IsOpen)
                        {
                            this.serialPort.Open();
                            operate();
                            this.serialPort.Close();
                            break;
                        }
                        CancellationToken token = this.cts.Token;
                        if (token.IsCancellationRequested)
                        {
                            throw new Exception("Process end");
                        }
                        Thread.Sleep((int)(200 + this.r.Next(200, 800)));
                    }
                }
            }
            catch (Exception exception)
            {
                if (exception is UnauthorizedAccessException)
                {
                    this.TryOperate(operate);
                }
                else if (exception is IOException)
                {
                    this.model.SetOperationStatus(this.serialPort.PortName + "SerialPortError", true);
                    //((StationDataModel)this.model).IsRealTime = false;
                }
                else
                {
                    if (exception is TimeoutException)
                    {
                        this.queue.Clear();
                    }
                    if (this.serialPort.IsOpen)
                    {
                        this.serialPort.Close();
                    }
                    this.model.SetOperationStatus(exception.Message + "(" + this.serialPort.PortName + ")", true);
                    //(StationDataModel)this.model).IsRealTime = false;
                }
            }
        }

        public bool IsNew =>
            !this.cts.IsCancellationRequested;

        public delegate void ModbusOperationDelegate();
    }
}
