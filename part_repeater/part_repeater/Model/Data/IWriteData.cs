namespace part_repeater.Model.Data
{
    public interface IWriteData<T> : IData
    {
        T[] WriteArray { get; }
    }
}
