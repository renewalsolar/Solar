namespace part_repeater
{
    internal static class Monitor
    {
        public static readonly int[] RealTimeDataRegOffset;
        public static readonly string[][] ArrayStatus;
        public static readonly string[][] ChargeStatus;
        public static readonly string[][] BatteryStatus;
        public static readonly string[][] LoadStatus;
        public static readonly string[][] ControllerStatus;
        static Monitor()
        {
            RealTimeDataRegOffset = new int[] { 0, 1, 2, 3, 12, 13, 14, 15, 16, 17, 26 };

            string[][] strArray = new string[9][];
            strArray[0] = new string[] { "Input", "Green" };
            strArray[1] = new string[] { "CutOut", "Black" };
            strArray[2] = new string[] { "ChargeMOSTShortCircuit", "Red" };
            strArray[3] = new string[] { "AntireverseMOSTShortCircuit", "Red" };
            strArray[4] = new string[] { "MOSTOpenCircuit", "Red" };
            strArray[5] = new string[] { "OutputOverCurrent", "Red" };
            strArray[6] = new string[] { "NotConnectToController", "Black" };
            strArray[7] = new string[] { "PhotocellVoltTooHigh", "Red" };
            strArray[8] = new string[] { "PhotocellVoltError", "Red" };
            ArrayStatus = strArray;
            string[][] strArray11 = new string[4][];
            strArray11[0] = new string[] { "NotCharging", "Black" };
            strArray11[1] = new string[] { "FloatCharge", "Green" };
            strArray11[2] = new string[] { "RaisingCharge", "Green" };
            strArray11[3] = new string[] { "EqualizingCharge", "Green" };
            ChargeStatus = strArray11;
            string[][] strArray16 = new string[8][];
            strArray16[0] = new string[] { "Normal", "Green" };
            strArray16[1] = new string[] { "Overvoltage", "Red" };
            strArray16[2] = new string[] { "Undervoltage", "Black" };
            strArray16[3] = new string[] { "Overdischarge", "Red" };
            strArray16[4] = new string[] { "BatteryError", "Red" };
            strArray16[5] = new string[] { "OverMaxTemp", "Red" };
            strArray16[6] = new string[] { "UnderMinTemp", "Red" };
            strArray16[7] = new string[] { "InnerResistanceError", "Red" };
            BatteryStatus = strArray16;
            string[][] strArray25 = new string[13][];
            strArray25[0] = new string[] { "On", "Green" };
            strArray25[1] = new string[] { "Off", "Black" };
            strArray25[2] = new string[] { "Overload", "Red" };
            strArray25[3] = new string[] { "ShortCircuit", "Red" };
            strArray25[4] = new string[] { "MOSTShortCircuit", "Red" };
            strArray25[5] = new string[] { "LoadOverCurrent", "Red" };
            strArray25[6] = new string[] { "OutputOvervoltage", "Red" };
            strArray25[7] = new string[] { "BoosterOvervoltage", "Red" };
            strArray25[8] = new string[] { "HighSideShortCircuit", "Red" };
            strArray25[9] = new string[] { "OutputVoltageError", "Black" };
            strArray25[10] = new string[] { "UnableToStopDischarging", "Red" };
            strArray25[11] = new string[] { "UnableToDischarge", "Black" };
            strArray25[12] = new string[] { "LoadOpenCircuit", "Black" };
            LoadStatus = strArray25;
            string[][] strArray39 = new string[4][];
            strArray39[0] = new string[] { "Normal", "Green" };
            strArray39[1] = new string[] { "Overheating", "Red" };
            strArray39[2] = new string[] { "RatedVoltageError", "Red" };
            strArray39[3] = new string[] { "ThreeWayImbalance", "Red" };
            ControllerStatus = strArray39;
        }
    }


}
