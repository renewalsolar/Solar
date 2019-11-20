namespace part_repeater.Model
{
    using System;
    using System.IO.Ports;

    internal class ModelFactory
    {
        public static IStationDataModel CreateStationDataModel(IStationInfoModel stationInfo) =>
            new StationDataModel(stationInfo);

        public static IStationDataModel CreateStationDataModel(string stationName, SerialPort serialPort, byte slaveID) =>
            new StationDataModel(stationName, serialPort, slaveID);

    }
}
