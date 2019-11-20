namespace part_repeater.Model.GlobalManager
{
    using part_repeater.Model;
    using System.Collections.Generic;

    internal class StationDataManager
    {
        private List<IStationDataModel> stationDataList = new List<IStationDataModel>();
        private static StationDataManager defaultInstance = new StationDataManager();

        StationDataManager() { }

        public void Add(IStationDataModel stationData)
        {
            this.stationDataList.Add(stationData);
            stationData.Processor.StartProcess();
        }

        public void RestartProcessors()
        {
            foreach (StationDataModel model in this.stationDataList)
            {
                model.Processor.Restart();
            }
        }

        public static StationDataManager Default =>
            defaultInstance;

        public IStationDataModel this[string ModelName] =>
            this.stationDataList.Find(m => m.StationName == ModelName);



    }
}
