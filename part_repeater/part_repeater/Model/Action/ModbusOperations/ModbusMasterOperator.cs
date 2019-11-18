namespace part_repeater.Model.Action.ModbusOperations
{
    using Modbus.ModbusExtension.Device;
    using System;
    using System.IO.Ports;
    using System.Threading;

    internal class ModbusMasterOperator
    {
        private SerialPort port;
        private byte slaveID;
        private IModbusExtensionMaster master;
        private int delayMS = 500;

        public ModbusMasterOperator(SerialPort Port, byte SlaveID)
        {
            this.port = Port;
            this.slaveID = SlaveID;
        }

        public int AuthenticateUser(byte logInLogOut, string userName, string password)
        {
            this.CreateMaster();
            return this.master.AuthenticateIdentity(this.slaveID, logInLogOut, userName, password);
        }

        private void CreateMaster()
        {
            this.master = ModbusExtensionSerialMaster.CreateRtu(this.port);
            this.master.Transport.ReadTimeout = 1000;
            this.master.Transport.WriteTimeout = 1000;
            this.master.Transport.Retries = 1;
            this.master.Transport.WaitToRetryMilliseconds = 500;
            this.WaitHub();
        }

        public ushort?[] ForceToReadRegisters(ushort startAddress, ushort numberOfPoints)
        {
            this.CreateMaster();
            return this.master.ForceToReadMultipleRegister(this.slaveID, startAddress, numberOfPoints);
        }

        public bool[] ReadDeviceCoils(ushort startAddress, ushort coilNumber)
        {
            this.CreateMaster();
            return this.master.ReadCoils(this.slaveID, startAddress, coilNumber);
        }

        public ushort[] ReadDeviceHoldingRegister(ushort startAddress, ushort registerNumber)
        {
            this.CreateMaster();
            return this.master.ReadHoldingRegisters(this.slaveID, startAddress, registerNumber);
        }

        public byte ReadDeviceID()
        {
            this.CreateMaster();
            return this.master.ReadDeviceID();
        }

        public string[] ReadDeviceInformation(byte readDeivceInfoCode, byte objectID)
        {
            this.CreateMaster();
            return this.master.ReadDeviceInformation(this.slaveID, readDeivceInfoCode, objectID);
        }

        public ushort[] ReadDeviceInputRegister(ushort startAddress, ushort registerNumber)
        {
            this.CreateMaster();
            return this.master.ReadInputRegisters(this.slaveID, startAddress, registerNumber);
        }

        public bool[] ReadDeviceInputs(ushort startAddress, ushort inputsNumber)
        {
            this.CreateMaster();
            return this.master.ReadInputs(this.slaveID, startAddress, inputsNumber);
        }

        public void SetDeviceID(byte slaveID)
        {
            this.CreateMaster();
            this.master.AlterDeviceID(slaveID);
        }

        private void WaitHub()
        {
            Thread.Sleep(this.delayMS);
        }

        public void WriteDeviceCoil(ushort coilAddress, bool coilValue)
        {
            this.CreateMaster();
            this.master.WriteSingleCoil(this.slaveID, coilAddress, coilValue);
        }

        public void WriteDeviceCoil(ushort startAddress, bool[] coilValue)
        {
            this.CreateMaster();
            this.master.WriteMultipleCoils(this.slaveID, startAddress, coilValue);
        }

        public void WriteDeviceHoldingRegister(ushort startAddress, ushort[] registerValue)
        {
            this.CreateMaster();
            this.master.WriteMultipleRegisters(this.slaveID, startAddress, registerValue);
        }
    }
}
