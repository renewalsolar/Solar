namespace part_repeater.Controller
{
    using part_repeater.View;
    public interface IModbusController
    {
        void RequestUpdateRealTimeMonitor(string stationName);
        void RequestRestartProcessors();
    }
}
