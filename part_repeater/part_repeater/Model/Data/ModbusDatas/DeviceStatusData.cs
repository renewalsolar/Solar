namespace part_repeater.Model.Data.ModbusDatas
{
    using part_repeater;

    internal class DeviceStatusData : part_repeater.Model.Data.Data
    {
        private string stationName;
        private string arrayStatus;
        private string chargeStatus;
        private string batteryStatus;
        private string loadStatus;
        private string controllerStatus;
        private string[] colors;

        public DeviceStatusData(string stationName)
        {
            this.colors = new string[5];
            this.stationName = stationName;
        }

        public DeviceStatusData(string stationName, ushort[] data)
        {
            this.stationName = stationName;
            this.colors = new string[5];
            this.Initialize<ushort>(data);
        }

        public override string[] GetDisplayArray()
        {
            string[] strArray = new string[] { this.arrayStatus, this.chargeStatus, this.batteryStatus, this.loadStatus, this.controllerStatus, this.colors[0], this.colors[1], this.colors[2], this.colors[3] };
            strArray[9] = this.colors[4];
            strArray[10] = this.stationName;
            return strArray;
        }

        public override void Initialize<T>(T[] data)
        {
            int[] numArray2 = DataConverter.UshortsToStatusIndexes(data as ushort[]);
            this.arrayStatus = Monitor.ArrayStatus[numArray2[0]][0];
            this.chargeStatus = Monitor.ChargeStatus[numArray2[1]][0];
            this.batteryStatus = Monitor.BatteryStatus[numArray2[2]][0];
            this.loadStatus = Monitor.LoadStatus[numArray2[3]][0];
            this.controllerStatus = Monitor.ControllerStatus[numArray2[4]][0];
            this.colors[0] = Monitor.ArrayStatus[numArray2[0]][1];
            this.colors[1] = Monitor.ChargeStatus[numArray2[1]][1];
            this.colors[2] = Monitor.BatteryStatus[numArray2[2]][1];
            this.colors[3] = Monitor.LoadStatus[numArray2[3]][1];
            this.colors[4] = Monitor.ControllerStatus[numArray2[4]][1];
            base.NotifyObserver();
        }
    }
}
