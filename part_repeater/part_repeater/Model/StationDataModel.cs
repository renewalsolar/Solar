using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace part_repeater.Model
{
    using part_repeater.Model.Action.ModbusOperations;
    using part_repeater.Model.Data.ModbusDatas;
    using part_repeater.Model.GlobalManager;
    using part_repeater.Model.Data;
    using System.IO.Ports;

    using part_repeater.Model.Action;

    internal class StationDataModel : IStationDataModel
    {
        private string stationName;
        private SerialPort serialPort;
        private byte slaveID;
        private ModbusOperationProcessor processor;
        private DeviceRealTimeData realTimeData;
        private OperationStatusData operationStatus;

        public StationDataModel(IStationInfoModel stationInfo) : this(stationInfo.StationName, SerialPortManager.Default[stationInfo.PortName], stationInfo.ControllerID)
        {
        }
        public StationDataModel(string stationName, SerialPort serialPort, byte slaveID)
        {
            this.stationName = stationName;
            this.serialPort = serialPort;
            this.slaveID = slaveID;
            this.processor = new ModbusOperationProcessor(this.serialPort, this);

            this.realTimeData = new DeviceRealTimeData();

            this.operationStatus = (serialPort == null) ? new OperationStatusData() : new OperationStatusData(stationName, serialPort.PortName);

        }
        public void ReadRealTimeData()
        {
            this.realTimeData.Initialize<ushort>(OperationFactory.CreateRealTimeDataReadOperation(this.serialPort, this.slaveID).Operate());
        }

        public void ResetProcess(string portName)
        {
            if ((this.serialPort != null) && ((this.serialPort.PortName == portName) && !this.processor.IsNew))
            {
                this.processor = new ModbusOperationProcessor(this.serialPort, this);
                this.processor.StartProcess();
            }
        }

        public void SetOperationStatus(string status, bool isError)
        {
            string str = null;
            if (status != null)
            {
                str = this.stationName + ": " + status;
            }
            string[] data = new string[] { str, isError.ToString() };
            this.operationStatus.Initialize<string>(data);
        }

        public bool IsRealTime
        {
            set =>
                this.operationStatus.IsRealTime = value;
        }

        public string StationName =>
            this.stationName;

        public string PortName =>
            this.serialPort.PortName;

        public ModbusOperationProcessor Processor =>
            this.processor;

        public IData RealTimeData =>
            this.realTimeData;

    }
}
