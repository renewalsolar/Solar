namespace part_repeater
{
    using System;
    using System.Collections.Generic;
    using System.Data;
    using Modbus.Utility;
    using System.IO.Ports;
    using System.Collections;
    using System.Net;


    public static class DataConverter
    {
        // Methods
        public static int BoolToIndex(bool dataH, bool dataL) =>
            ((Convert.ToInt32(dataH) << 1) | Convert.ToInt32(dataL));

        public static List<string>[] DataTableToListArray(DataTable dataTable)
        {
            List<string>[] listArray = new List<string>[dataTable.Rows.Count];
            for (int i = 0; i < listArray.Length; i++)
            {
                listArray[i] = new List<string>();
            }
            int index = 0;
            while (index < dataTable.Rows.Count)
            {
                int num3 = 0;
                while (true)
                {
                    if (num3 >= dataTable.Columns.Count)
                    {
                        index++;
                        break;
                    }
                    listArray[index].Add(dataTable.Rows[index][num3].ToString());
                    num3++;
                }
            }
            return listArray;
        }

        public static int DataToStatusIndex(ushort regData, bool[] inputsData, out int inputVolStatusIndex, out int outputPowerStatusIndex)
        {
            inputVolStatusIndex = (regData & 0xc000) >> 14;
            outputPowerStatusIndex = (regData & 0x3000) >> 12;
            return (((regData & 0x40) == 0) ? (((regData & 0x800) == 0) ? ((outputPowerStatusIndex != 3) ? ((inputVolStatusIndex != 2) ? ((inputVolStatusIndex != 1) ? (((regData & 0x80) == 0) ? (((regData & 0x100) == 0) ? (!inputsData[1] ? (!inputsData[0] ? (((regData & 2) != 0) ? (((regData & 1) != 0) ? 12 : 11) : 10) : 9) : 8) : 7) : 6) : 5) : 4) : 3) : 2) : 0);
        }

        public static ushort[] DateTimeToUshorts(DateTime dateTime)
        {
            List<byte> list = new List<byte> {
            (byte) dateTime.Minute,
            (byte) dateTime.Second,
            (byte) dateTime.Day,
            (byte) dateTime.Hour,
            (byte) (dateTime.Year - 0x7d0),
            (byte) dateTime.Month
        };
            return ModbusUtility.NetworkBytesToHostUInt16(list.ToArray());
        }

        public static ushort[] DoubleToUshorts(double d)
        {
            uint num = Convert.ToUInt32((d * 100.0).ToString("0"));
            return new ushort[] { ((ushort)num), ((ushort)(num >> 0x10)) };
        }

        public static ushort[] FloatArrayToUshortArray(float[] data) =>
            Array.ConvertAll<float, ushort>(data, new Converter<float, ushort>(DataConverter.FloatToUshort));

        public static ushort FloatToUshort(float f) =>
            ((ushort)Convert.ToInt16((f * 100f).ToString("0")));

        public static string[] SerialPortToStringArray(SerialPort serialPort) =>
            new string[] { serialPort.PortName, serialPort.BaudRate.ToString(), serialPort.DataBits.ToString(), ((int)serialPort.StopBits).ToString(), ((int)serialPort.Parity).ToString() };

        public static SerialPort StringArrayToSerialPort(string[] s)
        {
            SerialPort port = new SerialPort(s[0])
            {
                BaudRate = Convert.ToInt32(s[1]),
                DataBits = Convert.ToInt32(s[2])
            };
            switch (Convert.ToInt32(s[3]))
            {
                case 0:
                    port.StopBits = StopBits.None;
                    break;

                case 1:
                    port.StopBits = StopBits.One;
                    break;

                case 2:
                    port.StopBits = StopBits.Two;
                    break;

                default:
                    break;
            }
            switch (Convert.ToInt32(s[4]))
            {
                case 0:
                    port.Parity = Parity.None;
                    break;

                case 1:
                    port.Parity = Parity.Odd;
                    break;

                case 2:
                    port.Parity = Parity.Even;
                    break;

                default:
                    break;
            }
            return port;
        }

        public static float[] UshortArrayToFloatArray(ushort[] data) =>
            Array.ConvertAll<ushort, float>(data, new Converter<ushort, float>(DataConverter.UshortToFloat));

        public static DateTime UshortsToDateTime(ushort[] data)
        {
            string[] strArray = new string[] { data[2].ToString(), ":", data[1].ToString(), ":", data[0].ToString() };
            return Convert.ToDateTime(string.Concat(strArray));
        }

        public static double[] UshortsToDoubles(ushort[] data)
        {
            List<double> list = new List<double>();
            for (int i = 0; i < data.Length; i += 2)
            {
                list.Add(UshortToDouble(data[i + 1], data[i]));
            }
            return list.ToArray();
        }

        public static int UshortsToLoadControlMode(ushort controlMode, ushort[] value)
        {
            switch (controlMode)
            {
                case 0:
                    return value[1];

                case 1:
                case 2:
                    return (controlMode + 1);

                case 3:
                    return 5;

                case 4:
                    return 6;

                case 5:
                    return 7;
            }
            return -1;
        }

        public static int UshortsToLoadControlMode11(ushort controlMode, ushort[] value)
        {
            switch (controlMode)
            {
                case 0:
                    return value[1];

                case 1:
                case 2:
                    return (controlMode + 1);

                case 3:
                    return (4 + value[0]);

                case 4:
                    return 6;

                case 5:
                    return 7;
            }
            return -1;
        }

        public static int[] UshortsToStatusIndexes(ushort[] data)
        {
            int[] numArray = new int[5];
            int num = data[0] & 15;
            int num2 = (data[0] & 240) >> 4;
            BitArray array = new BitArray(BitConverter.GetBytes(data[0]));
            BitArray array2 = new BitArray(BitConverter.GetBytes(data[1]));
            BitArray array3 = new BitArray(BitConverter.GetBytes(data[2]));
            numArray[1] = (data[1] & 12) >> 2;
            numArray[4] = !array[15] ? (!array2[6] ? data[3] : 3) : 2;
            numArray[0] = !array2[13] ? (!array2[11] ? (!array2[12] ? (!array2[10] ? ((((data[1] & 0xc000) >> 14) == 0) ? ((numArray[1] == 0) ? 1 : 0) : (((data[1] & 0xc000) >> 14) + 5)) : 5) : 4) : 3) : 2;
            if ((num == 0) && (num2 == 0))
            {
                numArray[2] = 0;
            }
            else if (array[8])
            {
                numArray[2] = 7;
            }
            else if (num != 0)
            {
                numArray[2] = num;
            }
            else if (num2 != 0)
            {
                numArray[2] = num2 + 4;
            }
            if (!array3[1])
            {
                numArray[3] = array3[0] ? 0 : 1;
            }
            else if (array2[8] || array3[11])
            {
                numArray[3] = 3;
            }
            else if (array2[7])
            {
                numArray[3] = 4;
            }
            else if (array2[9])
            {
                numArray[3] = 5;
            }
            else if (array2[5])
            {
                numArray[3] = 12;
            }
            else if (array3[12] && array3[13])
            {
                numArray[3] = 2;
            }
            else
            {
                int num3 = 4;
                while (true)
                {
                    if (num3 < 7)
                    {
                        if (!array3[num3])
                        {
                            num3++;
                            continue;
                        }
                        numArray[3] = num3 + 2;
                    }
                    for (int i = 8; i < 11; i++)
                    {
                        if (array3[i])
                        {
                            numArray[3] = i + 1;
                            break;
                        }
                    }
                    break;
                }
            }
            return numArray;
        }

        public static DateTime UshortToDateTime(ushort data)
        {
            byte[] bytes = BitConverter.GetBytes((ushort)IPAddress.HostToNetworkOrder((short)data));
            return Convert.ToDateTime(bytes[0].ToString() + ":" + bytes[1].ToString() + ":00");
        }

        public static double UshortToDouble(ushort dataH, ushort dataL) =>
            (((double)((dataH << 0x10) + dataL)) / 100.0);

        public static float UshortToFloat(ushort data) =>
            (((float)data) / 100f);

        public static DateTime[] UshortToHourMinute(ushort data)
        {
            List<DateTime> list = new List<DateTime>();
            foreach (byte num in BitConverter.GetBytes((ushort)IPAddress.HostToNetworkOrder((short)data)))
            {
                list.Add(Convert.ToDateTime("00:" + num.ToString()));
            }
            return list.ToArray();
        }

        public static bool Value1Smaller(string value1, string value2) =>
            (Convert.ToDouble(value1) < Convert.ToDouble(value2));

        public static bool Value1SmallerOrEquil(string value1, string value2) =>
            (Convert.ToDouble(value1) <= Convert.ToDouble(value2));
    }

}
