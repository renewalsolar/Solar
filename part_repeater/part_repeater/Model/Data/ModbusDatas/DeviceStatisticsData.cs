namespace part_repeater.Model.Data.ModbusDatas
{
    using part_repeater;
    using System.Linq;
    using Unme.Common;

    internal class DeviceStatisticsData : part_repeater.Model.Data.Data
    {
        private float batMaxVoltage;
        private float batMinVoltage;
        private double dailyConsumption;
        private double monthlyConsumption;
        private double annualConsumption;
        private double totalConsumption;
        private double dailyChargeQuantity;
        private double monthlyChargeCapacity;
        private double annualChargeCapacity;
        private double totalChargeQuantity;
        private string stationName;

        public DeviceStatisticsData(string stationName)
        {
            this.stationName = stationName;
        }

        public DeviceStatisticsData(string stationName, ushort[] data)
        {
            this.Initialize<ushort>(data);
            this.stationName = stationName;
        }

        public override string[] GetDisplayArray()
        {
            string[] strArray = new string[] { this.batMaxVoltage.ToString("0.00"), this.batMinVoltage.ToString("0.00"), this.dailyConsumption.ToString("0.00"), this.monthlyConsumption.ToString("0.00"), this.annualConsumption.ToString("0.00"), this.totalConsumption.ToString("0.00"), this.dailyChargeQuantity.ToString("0.00"), this.monthlyChargeCapacity.ToString("0.00"), this.annualChargeCapacity.ToString("0.00") };
            strArray[9] = this.totalChargeQuantity.ToString("0.00");
            strArray[10] = this.stationName;
            return strArray;
        }

        public override void Initialize<T>(T[] data)
        {
            ushort[] source = data as ushort[];
            this.batMaxVoltage = DataConverter.UshortToFloat(source[0]);
            this.batMinVoltage = DataConverter.UshortToFloat(source[1]);
            double[] numArray2 = DataConverter.UshortsToDoubles(source.Slice<ushort>(2, (source.Length - 2)).ToArray<ushort>());
            this.dailyConsumption = numArray2[0];
            this.monthlyConsumption = numArray2[1];
            this.annualConsumption = numArray2[2];
            this.totalConsumption = numArray2[3];
            this.dailyChargeQuantity = numArray2[4];
            this.monthlyChargeCapacity = numArray2[5];
            this.annualChargeCapacity = numArray2[6];
            this.totalChargeQuantity = numArray2[7];
            base.NotifyObserver();
        }
    }
}
