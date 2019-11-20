namespace part_repeater.Model.Action.ModbusOperations
{
    using part_repeater;
    using System;
    using System.Collections.Generic;
    using System.IO.Ports;

    using System.Net;
    using System.Net.Json;
    using System.Text;
    using System.IO;

    internal class RealTimeDataReadOperation : ModbusOperation<ushort>
    {
        private const string SERVER_URL = "http://192.168.0.2:3001/api/pannel/update/5d9754d15f88c22e80202f0c";

        private JsonObjectCollection obj = new JsonObjectCollection();

        public void SendData(ushort[] data) {
            obj.Add(new JsonStringValue("output", data[2].ToString()));

            SendHTTP(obj.ToString());
        }

        private String SendHTTP(string sEntity)
        {
            int nStartTime = 0;
            string result = "";
            string strMsg = string.Empty;
            nStartTime = Environment.TickCount;
                       
            HttpWebRequest request = null;
            HttpWebResponse response = null;
            try
            {
                Uri url = new Uri(SERVER_URL);
                request = (HttpWebRequest)WebRequest.Create(url);
                request.Method = WebRequestMethods.Http.Post;
                request.Timeout = 5000;


                // 인코딩1 - UTF-8
                byte[] data = Encoding.UTF8.GetBytes(sEntity);
                request.ContentType = "application/json";
                request.ContentLength = data.Length;

                // 데이터 전송
                Stream dataStream = request.GetRequestStream();
                dataStream.Write(data, 0, data.Length);
                dataStream.Close();

                // 전송응답
                response = (HttpWebResponse)request.GetResponse();
                Stream responseStream = response.GetResponseStream();
                StreamReader streamReader = new StreamReader(responseStream, Encoding.UTF8);
                result = streamReader.ReadToEnd();

                // 연결닫기
                streamReader.Close();
                responseStream.Close();
                response.Close();
            }
            catch (Exception ex)
            {
                return result;
            }
            return result;
        }

        public RealTimeDataReadOperation(SerialPort serialPort, byte slaveID) : base(serialPort, slaveID)
        {
        }

        public override ushort[] Operate()
        {
            List<ushort> list = new List<ushort>();
            ushort?[] nullableArray = base.Master.ForceToReadRegisters(0x3100, 0x1b);
            int length = Monitor.RealTimeDataRegOffset.Length;
            ushort[] collection = new ushort[length];
            for (int i = 0; i < length; i++)
            {
                int index = Monitor.RealTimeDataRegOffset[i];
                if (nullableArray[index] == null)
                {
                    throw new RegisterNotFoundException("RegisterNotFound");
                }
                collection[i] = nullableArray[index].Value;
            }
            list.AddRange(collection);
            list.AddRange(base.Master.ReadDeviceInputRegister(0x331a, 3));
            SendData(list.ToArray());
            return list.ToArray();
        }
    }
}
