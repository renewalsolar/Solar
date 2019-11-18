namespace part_repeater.Model.Data.ModbusDatas
{
    internal class DeviceRealTimeData : part_repeater.Model.Data.Data
    {
        private float arrayVoltage;
        private float arrayCurrent;
        private double arrayPower;
        private float loadVoltage;
        private float loadCurrent;
        private double loadPower;
        private float batteryTemp;
        private float deviceTemp;
        private ushort batterySOC;
        private float batteryVoltage;
        private double batteryCurrent;

        public DeviceRealTimeData()
        {
        }

        public DeviceRealTimeData(ushort[] data)
        {
            this.Initialize<ushort>(data);
        }

        public override string[] GetDisplayArray()
        {
            string[] strArray = new string[] { this.arrayVoltage.ToString("0.00"), this.arrayCurrent.ToString("0.00"), this.arrayPower.ToString("0.00"), this.loadVoltage.ToString("0.00"), this.loadCurrent.ToString("0.00"), this.loadPower.ToString("0.00"), this.batteryTemp.ToString("0.00"), this.deviceTemp.ToString("0.00"), this.batterySOC.ToString() };
            strArray[9] = this.batteryVoltage.ToString("0.00");
            strArray[10] = this.batteryCurrent.ToString("0.00");
            return strArray;
        }

        public override void Initialize<T>(T[] data)
        {
            ushort[] numArray = data as ushort[];
            float[] numArray2 = DataConverter.UshortArrayToFloatArray(numArray);
            this.arrayVoltage = numArray2[0];
            this.arrayCurrent = numArray2[1];
            this.arrayPower = DataConverter.UshortToDouble(numArray[3], numArray[2]);
            this.loadVoltage = numArray2[4];
            this.loadCurrent = numArray2[5];
            this.loadPower = DataConverter.UshortToDouble(numArray[7], numArray[6]);
            this.batteryTemp = ((float)((short)numArray[8])) / 100f;
            this.deviceTemp = ((float)((short)numArray[9])) / 100f;
            this.batterySOC = numArray[10];
            this.batteryVoltage = numArray2[11];
            this.batteryCurrent = DataConverter.UshortToDouble(numArray[13], numArray[12]);
            base.NotifyObserver();
        }
    }
}
