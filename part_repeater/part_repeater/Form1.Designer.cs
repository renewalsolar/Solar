namespace part_repeater
{
    partial class Form1 
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.serialPortNameComboBox = new System.Windows.Forms.ComboBox();
            this.label1 = new System.Windows.Forms.Label();
            this.btn_start = new System.Windows.Forms.Button();
            this.timer = new System.Windows.Forms.Timer(this.components);
            this.txt_volt = new System.Windows.Forms.TextBox();
            this.txt_current = new System.Windows.Forms.TextBox();
            this.txt_power = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.ctrlManager = new part_repeater.Controller.ControllerManager();
            this.ctrlManager.ModbusController = part_repeater.Controller.ControllerFactory.CreateModbusController();

            this.SuspendLayout();
            // 
            // serialPortNameComboBox
            // 
            this.serialPortNameComboBox.FormattingEnabled = true;
            this.serialPortNameComboBox.Location = new System.Drawing.Point(96, 41);
            this.serialPortNameComboBox.Name = "serialPortNameComboBox";
            this.serialPortNameComboBox.Size = new System.Drawing.Size(121, 23);
            this.serialPortNameComboBox.TabIndex = 0;
            // 
            // btn_start
            // 
            this.btn_start.Location = new System.Drawing.Point(267, 30);
            this.btn_start.Name = "btn_start";
            this.btn_start.Size = new System.Drawing.Size(85, 42);
            this.btn_start.TabIndex = 2;
            this.btn_start.Text = "시작";
            this.btn_start.UseVisualStyleBackColor = true;
            this.btn_start.Click += new System.EventHandler(this.btn_start_Click);
            // 
            // timer
            // 
            this.timer.Tick += new System.EventHandler(this.timer_Tick);
            // 
            // txt_volt
            // 
            this.txt_volt.Location = new System.Drawing.Point(96, 102);
            this.txt_volt.Name = "txt_volt";
            this.txt_volt.Size = new System.Drawing.Size(121, 25);
            this.txt_volt.TabIndex = 3;
            // 
            // txt_current
            // 
            this.txt_current.Location = new System.Drawing.Point(96, 133);
            this.txt_current.Name = "txt_current";
            this.txt_current.Size = new System.Drawing.Size(121, 25);
            this.txt_current.TabIndex = 3;
            // 
            // txt_power
            // 
            this.txt_power.Location = new System.Drawing.Point(96, 164);
            this.txt_power.Name = "textBox3";
            this.txt_power.Size = new System.Drawing.Size(121, 25);
            this.txt_power.TabIndex = 3;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(43, 44);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(47, 15);
            this.label1.TabIndex = 1;
            this.label1.Text = "PORT";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(43, 105);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(31, 15);
            this.label2.TabIndex = 1;
            this.label2.Text = "Volt";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(43, 136);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(53, 15);
            this.label3.TabIndex = 1;
            this.label3.Text = "Current";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(43, 167);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(48, 15);
            this.label4.TabIndex = 1;
            this.label4.Text = "Power";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(436, 237);
            this.Controls.Add(this.txt_power);
            this.Controls.Add(this.txt_current);
            this.Controls.Add(this.txt_volt);
            this.Controls.Add(this.btn_start);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.serialPortNameComboBox);
            this.Name = "Form1";
            this.Text = "Form1";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ComboBox serialPortNameComboBox;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;

        private System.Windows.Forms.TextBox txt_volt;
        private System.Windows.Forms.TextBox txt_current;
        private System.Windows.Forms.TextBox txt_power;

        private System.Windows.Forms.Button btn_start;
        private System.Windows.Forms.Timer timer;

        private part_repeater.Controller.ControllerManager ctrlManager;


    }
}