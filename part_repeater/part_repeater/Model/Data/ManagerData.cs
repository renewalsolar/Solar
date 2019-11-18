namespace part_repeater.Model.Data
{
    internal class ManagerData : part_repeater.Model.Data.Data
    {
        public ManagerData(int manager)
        {
            this.Manager = manager;
        }

        public override string[] GetDisplayArray() =>
            null;

        public override void Initialize<T>(T[] data)
        {
            this.IsAddData = false;
            this.StationName = null;
        }

        public int Manager { get; set; }

        public bool IsAddData { get; set; }

        public string StationName { get; set; }

        public string PortName { get; set; }
    }
}
