/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.auton.options;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 * @author Atif_2
 */
public class DoNothing extends CommandGroup {
    
    public DoNothing() {
        
        addSequential(new WaitCommand(0));
    }
}
