using System;
using System.Windows.Forms;

using System.IO.Ports;
using part_repeater.Model.GlobalManager;
using part_repeater.Model.Data;
using part_repeater.Model.Data.ModbusDatas;
using part_repeater.View;


namespace part_repeater
{
    public partial class Form1 : Form, IView
    {
        private const int PERIOD = 15;
        private const string STATION_NAME = "solar";

        public Form1()
        {
            InitializeComponent();

            

            string[] portNames = SerialPort.GetPortNames();

            for (int i = 0; i < portNames.Length; i++)
                Console.WriteLine(portNames[i]);


            serialPortNameComboBox.Items.Clear();
            serialPortNameComboBox.Items.AddRange(portNames);

            if (this.serialPortNameComboBox.Items.Count != 0)
                this.serialPortNameComboBox.SelectedIndex = this.serialPortNameComboBox.Items.Count - 1;
        }

        private SerialPort CreateSerialPort()
        {
            string[] s = new string[] { this.serialPortNameComboBox.Text, "115200", "8", "1", "0" };
            return DataConverter.StringArrayToSerialPort(s);
        }

        private void btn_start_Click(object sender, EventArgs e)
        {
            SerialPort serialPort = this.CreateSerialPort();
            SerialPortManager.Default.Add(serialPort);
            int num;
            try
            {
                num = Convert.ToInt32(PERIOD) * 1000;
            }
            catch
            {
                MessageBox.Show("주기 설정 불가");
                return;
            }

            btn_start.Tag = timer.Enabled ? "StartMonitor" : "StopMonitor";
            timer.Enabled = !timer.Enabled;
            btn_start.Text = btn_start.Tag.ToString();

            serialPortNameComboBox.Enabled = !this.timer.Enabled;
            timer.Interval = num;
            if (timer.Enabled)
            {
                timer_Tick(null, null);
            }
            else
            {
                ctrlManager.ModbusController.RequestRestartProcessors();
            }
        }

        private void timer_Tick(object sender, EventArgs e)
        {
            this.ctrlManager.ModbusController.RequestUpdateRealTimeMonitor(STATION_NAME);
        }

        private delegate void myDelegate(DeviceRealTimeData data);

        private void below01(DeviceRealTimeData data)
        {
            string[] displayArray = data.GetDisplayArray();
            this.txt_volt.Text = displayArray[0];
            this.txt_current.Text = displayArray[1];
            this.txt_power.Text = displayArray[2];
        }

        private void UpdateRealTimeData(DeviceRealTimeData data)
        {
            base.BeginInvoke(new myDelegate(below01), data);
        }

        public void UpdateView(IData data)
        {
            UpdateRealTimeData((DeviceRealTimeData)data);
        }
    }
}