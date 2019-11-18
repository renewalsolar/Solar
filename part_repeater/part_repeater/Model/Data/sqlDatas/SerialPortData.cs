
namespace part_repeater.Model.Data.sqlDatas
{
    using part_repeater;
    internal class SerialPortData 
    {
        private System.IO.Ports.SerialPort serialPort;

        public SerialPortData()
        {
        }

        public SerialPortData(System.IO.Ports.SerialPort serialPort)
        {
            this.serialPort = serialPort;
        }

        public SerialPortData(string[] data)
        {
            this.Initialize<string>(data);
        }

        public string[] GetDisplayArray() =>
            null;

        public void Initialize<T>(T[] data)
        {
            string[] s = data as string[];
            this.serialPort = DataConverter.StringArrayToSerialPort(s);
        }

        public System.IO.Ports.SerialPort SerialPort =>
            this.serialPort;

        public string TableName =>
            "SerialPortSetting";

        public string[] WriteArray =>
            DataConverter.SerialPortToStringArray(this.serialPort);
    }
}
