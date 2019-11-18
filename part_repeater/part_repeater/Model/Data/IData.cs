namespace part_repeater.Model.Data
{
    using part_repeater.View;

    public interface IData
    {
        void AddObserver(IView view);
        string[] GetDisplayArray();
        void Initialize<T>(T[] data);
        void NotifyObserver();
        void RemoveObserver(IView view);
    }
}
