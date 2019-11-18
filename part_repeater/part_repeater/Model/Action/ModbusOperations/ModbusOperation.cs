namespace part_repeater.Model.Action.ModbusOperations
{
    using part_repeater.Model.Action;
    using part_repeater.Model.Data;
    using System;
    using System.IO.Ports;

    internal abstract class ModbusOperation<T> : IOperation<T>
    {
        protected IWriteData<T> WriteData;
        protected ModbusMasterOperator Master;

        public ModbusOperation(SerialPort port, byte slaveID)
        {
            this.Master = new ModbusMasterOperator(port, slaveID);
        }

        public abstract T[] Operate();
    }
}
