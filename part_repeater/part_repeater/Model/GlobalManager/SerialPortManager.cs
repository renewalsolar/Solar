namespace part_repeater.Model.GlobalManager
{
    using part_repeater.Model.Data;
    using System.Collections.Generic;
    using System.IO.Ports;

    internal class SerialPortManager
    {
        private List<SerialPort> serialPortList = new List<SerialPort>();
        private ManagerData data = new ManagerData(0);
        private static SerialPortManager defaultInstance = new SerialPortManager();

        public void Add(SerialPort serialPort)
        {
            this.serialPortList.Add(serialPort);
            //StationDataManager.Default.Initialize();
            this.data.IsAddData = true;
            this.data.PortName = serialPort.PortName;
            this.data.NotifyObserver();
        }

        public static SerialPortManager Default =>
            defaultInstance;
        
        public SerialPort this[string PortName] =>
            this.serialPortList.Find(s => s.PortName == PortName);


    }
}
