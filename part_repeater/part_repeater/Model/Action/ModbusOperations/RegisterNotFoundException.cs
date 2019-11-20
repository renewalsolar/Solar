namespace part_repeater.Model.Action.ModbusOperations
{
    using System;

    public class RegisterNotFoundException : Exception
    {
        public RegisterNotFoundException(string message) : base(message)
        {
        }
    }
}
