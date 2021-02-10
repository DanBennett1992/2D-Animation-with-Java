package javagames.render;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class myDisplayModeExample extends JFrame { 
	
	
	public class DisplayModeWrapper{
		
		private DisplayMode dm;
		
		DisplayModeWrapper(DisplayMode dm){
			this.dm = dm;
		}
		
		public boolean equals(DisplayModeWrapper other) {
			
			DisplayModeWrapper otherWrapper = (DisplayModeWrapper)other;
			
			if(otherWrapper.dm.getHeight()!=dm.getHeight()) {return false;}
			if(otherWrapper.dm.getWidth()!=dm.getWidth()) {return false;}
			
			return true;}
		
		public String toString() {
			
			return "Display: "+dm.getWidth()+" X "+ dm.getHeight() ;
			
		 }
	   }	
	

	  private JComboBox displayModes;
	  private GraphicsDevice graphicsDevice;
	  private DisplayMode currentMode;
	 
	  
	  myDisplayModeExample (){
		  
		  GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		  graphicsDevice = ge.getDefaultScreenDevice();
		  currentMode = graphicsDevice.getDisplayMode();
		  
	  }
	  
	  
	private DisplayModeWrapper [] listDisplayModes() {
		
		ArrayList <DisplayModeWrapper> list = new ArrayList<DisplayModeWrapper>();
		
		for (DisplayMode mode : graphicsDevice.getDisplayModes()) {
			
			DisplayModeWrapper wrap = new DisplayModeWrapper (mode);
			
			if (wrap.dm.getBitDepth()==32) {
				   if (!list.contains(wrap)) {
					   list.add(wrap);
				   }
			}
		}
	
		
		return (DisplayModeWrapper[]) list.toArray();
			 	 
	 }
	  
 
    
     private JPanel getMainPanel() {
    	 
    	 JPanel panel = new JPanel();
    	 
    	 displayModes= new JComboBox (listDisplayModes());
    	 
    	 panel.add(displayModes);
    	 
    	 JButton open = new JButton("Open Full Screen");
    	 ActionListener opener = (e)->{onEnterFullScreen();};
    	 open.addActionListener(opener);
    	 panel.add(open);
    	 
    	 JButton exit = new JButton();
    	 ActionListener exiter = (e)->{onExitFullScreen();};
    	 exit.addActionListener(exiter);
    	 panel.add(exit);
    	 
    	
		return panel;
		
     }
     
     
 
		 
		 protected void createAndShowGUI() { 
			 
			 Container canvas = getContentPane();
			 canvas.add(getMainPanel());
			 canvas.setIgnoreRepaint(true);
			 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			 setTitle("My Display Mode Example");
			 pack();
			 setVisible(true);
			 
		 }
		 
		 protected DisplayMode getSelectedMode() {
			 
			 DisplayModeWrapper selectedWrapper = (DisplayModeWrapper) displayModes.getSelectedItem();
			 
			 DisplayMode selectedMode =  selectedWrapper.dm;
			 
			 int width = selectedMode.getWidth();
			 int height = selectedMode.getHeight();
			 int bitDepth = selectedMode.getBitDepth();
			 int refreshRate = DisplayMode.REFRESH_RATE_UNKNOWN;
			 
			 DisplayMode newMode = new DisplayMode(width,height,bitDepth,refreshRate);
			
			 return newMode;
			 
		 }
		 
		 
		 protected void onEnterFullScreen() { 
			 
			 if (graphicsDevice.isFullScreenSupported()) {
				 graphicsDevice.setDisplayMode(getSelectedMode());
				 graphicsDevice.setFullScreenWindow(this);
			 }
			 
			
			 }
		 
		 protected void onExitFullScreen() { 
			 graphicsDevice.setDisplayMode(currentMode);
			 graphicsDevice.setFullScreenWindow(null);
			
		 }
		 
		 
		 public static void main( String[] args ) { 
			 
			 final DisplayModeExample app = new DisplayModeExample();
			 
		     SwingUtilities.invokeLater( new Runnable() { 
		    	         public void run(){app.createAndShowGUI();
		 } });
		 }
		    }






