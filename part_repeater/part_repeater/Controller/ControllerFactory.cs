namespace part_repeater.Controller
{
    internal class ControllerFactory
    {
        public static IModbusController CreateModbusController() =>
            new ModbusController();

        //public static IModbusController CreateModbusController(string stationName, SerialPort serialPort, byte slaveID) =>
        //    new ModbusController(stationName, serialPort, slaveID);

    }
}
