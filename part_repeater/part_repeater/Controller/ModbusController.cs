namespace part_repeater.Controller
{
    using System;
    using part_repeater.Model;
    using part_repeater.Model.GlobalManager;
    using part_repeater.Model.Action.ModbusOperations;

    class ModbusController : IModbusController
    {
        private const string STATION_NAME = "solar";
        private const string PORT_NAME = "COM4";
        private const byte CONTROLLER_ID = 0x01;

        private IStationDataModel model;
        public ModbusController()
        {
        }


        public void RequestRestartProcessors()
        {
            StationDataManager.Default.RestartProcessors();
        }

        public void RequestUpdateRealTimeMonitor(string stationName)
        {
            this.FindModelAndAddOperation(stationName, delegate
            {
                try
                {
                    //IStationDataModel model = StationDataManager.Default[stationName];
                    model = StationDataManager.Default[stationName];
                    ((StationDataModel)model).IsRealTime = true;
                    model.ReadRealTimeData();
                    model.SetOperationStatus(null, false);
                    ((StationDataModel)model).IsRealTime = false;
                }
                catch (Exception exception)
                {
                    Console.WriteLine(exception.ToString());
                    //this.ExceptionHandler(exception);
                }
            });
        }


        private void FindModelAndAddOperation(string stationName, ModbusOperationProcessor.ModbusOperationDelegate operation)
        {
            //Console.WriteLine(SerialPortManager.Default[PORT_NAME].ToString());
            StationDataManager.Default.Add(ModelFactory.CreateStationDataModel(STATION_NAME, SerialPortManager.Default[PORT_NAME], CONTROLLER_ID));
            StationDataManager.Default[stationName].Processor.Add(operation);
        }
    }
}
