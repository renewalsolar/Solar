namespace part_repeater.Model
{
    using part_repeater.Model.Action.ModbusOperations;
    using part_repeater.Model.Data;
    using System;

    public interface IStationDataModel
    {
        //void ReadDeviceStatus();
        void ReadRealTimeData();
        //void ReadStatisticsData();
        void ResetProcess(string portName);

        void SetOperationStatus(string status, bool isError);

        string StationName { get; }

        string PortName { get; }

        ModbusOperationProcessor Processor { get; }

        IData RealTimeData { get; }

    }
}
