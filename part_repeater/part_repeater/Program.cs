using System;
using System.Windows.Forms;

namespace part_repeater
{

    class Program
    {
        static void Main(string[] args)
        {
            //Console.WriteLine(Convert.ToInt32(ButtonKeyResource.ResourceManager.GetString(operationKey, ButtonKeyResource.Culture));

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());
        }
    }
}

