/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.auton;

import com.techhounds.OI;
import com.techhounds.commands.auton.options.DoNothing;
import com.techhounds.commands.auton.options.MoveIntoZone;
import com.techhounds.commands.auton.options.OneBallAutonCheezy;
import com.techhounds.commands.auton.options.TwoBallAuton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 * @author Atif Niyaz
 */
public class AutonChooser extends CommandGroup {
    
    public static final String [] AUTON_CHOICES = new String [] {"2 Ball Auton", "1 Ball Auton Cheezy", "Only Move Into Zone", "Do Nothing"};
   
    public static Command getSelected() {
        
        Command [] choices = new Command [] {
            new TwoBallAuton(),
            new OneBallAutonCheezy(),
            new MoveIntoZone(),
            new DoNothing()
        };
        
        return choices[OI.getInstance().getAutonChoice()];
    }
}
