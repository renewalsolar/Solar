namespace part_repeater.Model.Data.ModbusDatas
{
    using System;

    internal class OperationStatusData : part_repeater.Model.Data.Data
    {
        private string operationStatus;
        private bool isError;

        public OperationStatusData()
        {
        }

        public OperationStatusData(string[] data)
        {
            this.Initialize<string>(data);
        }

        public OperationStatusData(string stationName, string portName)
        {
            this.StationName = stationName;
            this.PortName = portName;
        }

        public override string[] GetDisplayArray() =>
            new string[] { this.operationStatus, this.isError.ToString() };

        public override void Initialize<T>(T[] data)
        {
            string[] strArray = data as string[];
            this.operationStatus = strArray[0];
            this.isError = Convert.ToBoolean(strArray[1]);
            base.NotifyObserver();
        }

        public bool IsRealTime { get; set; }

        public bool ShowDialog { get; set; }

        public string StationName { get; set; }

        public string PortName { get; set; }

        public bool IsError =>
            this.isError;
    }
}
