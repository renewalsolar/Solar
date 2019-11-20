namespace part_repeater.Model
{
    internal interface IStationInfoModel
    {
        string StationName { get; }

        byte ControllerID { get; }

        string PortName { get; }
    }
}
