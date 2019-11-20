namespace part_repeater.Model.Action
{
    public interface IOperation<T>
    {
        T[] Operate();
    }
}
