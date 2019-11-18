namespace part_repeater.Model.Action
{
    using part_repeater.Model.Action.ModbusOperations;
    using System.IO.Ports;

    internal class OperationFactory
    {
        //public static IOperation<ushort> CreateDeviceStatusReadOperation(SerialPort serialPort, byte slaveID) =>
        //    new DeviceStatusReadOperation(serialPort, slaveID);

        public static IOperation<ushort> CreateRealTimeDataReadOperation(SerialPort serialPort, byte slaveID) =>
            new RealTimeDataReadOperation(serialPort, slaveID);


        //public static IOperation<ushort> CreateStatisticsDataReadOperation(SerialPort serialPort, byte slaveID) =>
        //    new StatisticsDataReadOperation(serialPort, slaveID);


    }
}
