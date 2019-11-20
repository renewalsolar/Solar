namespace part_repeater.Model.Data
{
    using System.Collections.Generic;
    using part_repeater.View;

    public abstract class Data : IData
    {
        private List<IView> observerList = new List<IView>();


        public void AddObserver(IView view)
        {
            this.observerList.Add(view);
        }

        public abstract string[] GetDisplayArray();
        public abstract void Initialize<T>(T[] data);
        public void NotifyObserver()
        {
            try
            {
                foreach (IView view in this.observerList)
                {
                    view.UpdateView(this);
                }
            }
            catch
            {
            }
        }

        public void RemoveObserver(IView view)
        {
            this.observerList.Remove(view);
        }
    }
}
