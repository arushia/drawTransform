import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TransformArt {

	public static void main(String[] args) {

		MyGUI gui = new MyGUI();
	}
}

class MyGUI {

	// Attributes
	Color color = Color.RED;
	MyDrawingPanel drawingPanel;
	Color[][] pic = new Color[20][20];
	JColorChooser chooseCol;


	MyGUI() {

		// Create Java Window
		JFrame window = new JFrame("SimpleDraw");
		window.setBounds(100, 100, 900, 750);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem open = new JMenuItem("Open", 'o');
		open.addActionListener(new myAction());
		JMenuItem save = new JMenuItem("Save", 's');
		save.addActionListener(new myAction());
		file.add(open);
		file.add(save);
		JMenu edit = new JMenu("Edit");
		JMenuItem clear = new JMenuItem("Clear");
		clear.addActionListener(new myAction());
		edit.add(clear);
		menubar.add(file);
		menubar.add(edit);
		window.setJMenuBar(menubar);

		// Create GUI elements

		// JPanel to draw in
		drawingPanel = new MyDrawingPanel();
		drawingPanel.addMouseListener(new myMouse());
		drawingPanel.addMouseMotionListener(new myMotion());
		drawingPanel.setBounds(60, 20, 400,400);
		drawingPanel.setBorder(BorderFactory.createEtchedBorder());

		// JButton
		JButton button = new JButton("Reset");
		button.addActionListener(new myAction());
		button.setBounds(190, 510, 75, 20);

		chooseCol = new JColorChooser(Color.RED);
		chooseCol.getSelectionModel().addChangeListener(new myChange());
		chooseCol.setPreferredSize(new Dimension(470, 225));

		JButton up = new JButton("Shift Up");
		JButton down = new JButton("Shift Down");
		JButton right = new JButton("Shift Right");
		JButton left = new JButton("Shift Left");
		
		up.addActionListener(new myAction());
		up.setBounds(550, 50, 125, 40);
		down.addActionListener(new myAction());
		down.setBounds(550, 100, 125, 40);
		right.addActionListener(new myAction());
		right.setBounds(550, 150, 125, 40);
		left.addActionListener(new myAction());
		left.setBounds(550, 200, 125, 40);
		
		JButton rotright = new JButton("Rotate Right");
		JButton rotleft = new JButton("Rotate Left");
		
		rotright.addActionListener(new myAction());
		rotright.setBounds(550, 250, 125, 40);
		rotleft.addActionListener(new myAction());
		rotleft.setBounds(550, 300, 125, 40);
		
		JButton flvert = new JButton("Flip Vertically");
		JButton flhoriz = new JButton("Flip Horizontally");
		
		flvert.addActionListener(new myAction());
		flvert.setBounds(550, 350, 125, 40);
		flhoriz.addActionListener(new myAction());
		flhoriz.setBounds(550, 400, 125, 40);
		
		JButton morered = new JButton("More Red");
		JButton lessred = new JButton("Less Red");
		JButton moreblue = new JButton("More Blue");
		JButton lessblue = new JButton("Less Blue");
		JButton moregreen = new JButton("More Green");
		JButton lessgreen = new JButton("Less Green");
		
		morered.addActionListener(new myAction());
		morered.setBounds(700, 50, 125, 40);
		lessred.addActionListener(new myAction());
		lessred.setBounds(700, 100, 125, 40);
		moreblue.addActionListener(new myAction());
		moreblue.setBounds(700, 150, 125, 40);
		lessblue.addActionListener(new myAction());
		lessblue.setBounds(700, 200, 125, 40);
		moregreen.addActionListener(new myAction());
		moregreen.setBounds(700, 250, 125, 40);
		lessgreen.addActionListener(new myAction());
		lessgreen.setBounds(700, 300, 125, 40);
		
		JButton exvert = new JButton("Expand Vertically");
		JButton exhoriz = new JButton("Expand Horizontally");
		
		exvert.addActionListener(new myAction());
		exvert.setBounds(700, 350, 150, 40);
		exhoriz.addActionListener(new myAction());
		exhoriz.setBounds(700, 400, 150, 40);
		
		window.add(up);
		window.add(down);
		window.add(right);
		window.add(left);
		window.add(rotright);
		window.add(rotleft);
		window.add(flvert);
		window.add(flhoriz);
		window.add(morered);
		window.add(lessred);
		window.add(moreblue);
		window.add(lessblue);
		window.add(moregreen);
		window.add(lessgreen);
		window.add(exvert);
		window.add(exhoriz);

		// Add GUI elements to the Java window's ContentPane
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);

		JPanel colorPanel = new JPanel();
		colorPanel.setBorder(BorderFactory.createTitledBorder("Drawing Color"));

		colorPanel.setBounds(15, 425, 485, 650);
		colorPanel.add(chooseCol);

		mainPanel.add(drawingPanel);

		mainPanel.add(colorPanel);
		mainPanel.add(button);

		window.getContentPane().add(mainPanel);

		// Let there be light
		window.setVisible(true);
		clearDraw();

	}




	public Color[][] read(File fi) {
		Scanner in;
		Scanner inline;
		String line;
		Color[][] colo = new Color[20][20];
		int x = 0;
		int y = 0;
		int r;
		int g;
		int b;
		try {
			in = new Scanner(fi);
				while(in.hasNextLine()) {
					line = in.nextLine();
					if(line.equals("255")) {
						break;
					}
				}
				while (in.hasNextLine())
				{
					line = in.nextLine();
					if(line.charAt(0) == '#') {
			            line = in.nextLine();
			        }
					inline = new Scanner(line);
					while(inline.hasNextInt()) {
						r = inline.nextInt();
						g = inline.nextInt();
						b = inline.nextInt();
						Color yo = new Color(r, g, b);
						colo[y][x] = yo;
						y++;
					}
					x++;		
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		return colo;
	}

	public void clearDraw() {

		drawingPanel.repaint();
		drawingPanel.paintComponent(drawingPanel.getGraphics());
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 20; j++) {
				pic[i][j] = Color.WHITE;
			}
		}

	}
	
	public void update(Color[][] arr) {
		clearDraw();
		pic = arr;
		drawingPanel.repaint();
	}
	
	public Color[][] fillWhite(Color[][] arr) {
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[0].length; j++) {
				arr[i][j] = Color.WHITE;
			}
		}
		
		return arr;
	}
	
	
	/**
     * Shift the image left by the specified amount.
     * <p>
     * Any pixels shifted in from off screen should be filled with FILL_VALUE. This function <i>does
     * not modify the original image</i>.
     *
     * @param originalImage the image to shift to the left
     * @param amount the amount to shift the image to the left
     * @return the shifted image
     */
    public Color[][] shiftLeft(final Color[][] originalImage, final int amount) {
        Color[][] arr = new Color[originalImage.length][originalImage[0].length];

        arr = fillWhite(arr);

        for (int i = 0; i < originalImage.length - amount; i++) {
            for (int j = 0; j < originalImage[0].length; j++) {
                arr[i][j] = originalImage[i + amount][j];
            }
        }
        
        //update(arr);
        return arr;
    }
	
    /**
     * Shift the image right by the specified amount.
     * <p>
     * Any pixels shifted in from off screen should be filled with FILL_VALUE. This function <i>does
     * not modify the original image</i>.
     *
     * @param originalImage the image to shift to the right
     * @param amount the amount to shift the image to the right
     * @return the shifted image
     */
    public Color[][] shiftRight(final Color[][] originalImage, final int amount) {
        Color[][] arr = new Color[originalImage.length][originalImage[0].length];

        arr = fillWhite(arr);

        for (int i = 0; i < originalImage.length - amount; i++) {
            for (int j = 0; j < originalImage[0].length; j++) {
                arr[i + amount][j] = originalImage[i][j];
            }
        }

        return arr;
    }
    
    /**
     * Shift the image up by the specified amount.
     * <p>
     * Any pixels shifted in from off screen should be filled with FILL_VALUE. This function <i>does
     * not modify the original image</i>.
     *
     * @param originalImage the image to shift up
     * @param amount the amount to shift the image up
     * @return the shifted image
     */
    public Color[][] shiftUp(final Color[][] originalImage, final int amount) {
        Color[][] arr = new Color[originalImage.length][originalImage[0].length];

        arr = fillWhite(arr);

        for (int i = 0; i < originalImage.length; i++) {
            for (int j = 0; j < originalImage[0].length - amount; j++) {
                arr[i][j] = originalImage[i][j + amount];
            }
        }

        return arr;
    }
    
    /**
     * Shift the image down by the specified amount.
     * <p>
     * Any pixels shifted in from off screen should be filled with FILL_VALUE. This function <i>does
     * not modify the original image</i>.
     *
     * @param originalImage the image to shift down
     * @param amount the amount to shift the image down
     * @return the shifted image
     */
    public Color[][] shiftDown(final Color[][] originalImage, final int amount) {
        Color[][] arr = new Color[originalImage.length][originalImage[0].length];

        arr = fillWhite(arr);

        for (int i = 0; i < originalImage.length; i++) {
            for (int j = 0; j < originalImage[0].length - amount; j++) {
                arr[i][j + amount] = originalImage[i][j];
            }
        }

        return arr;
    }
    
    /**
     * Rotates a square array to the right.
     *
     * @param sq 2d array to be rotated
     * @return ro rotated array
     */

    public Color[][] rot(final Color[][] sq) {
        Color[][] ro = new Color[sq.length][sq[0].length];
        for (int i = 0; i < sq[0].length; i++) {
            for (int j = sq.length - 1; j >= 0; j--) {
                ro[i][j] = sq[j][i];
            }
        }



        Color[][] cool = new Color[ro.length][ro[0].length];
        for (int i = 0; i < ro.length; i++) {
            Color[] a = ro[i];
            for (int j = 0; j < ro[i].length; j++) {
                cool[ro.length - i - 1][j] = a[j];
            }

        }

        return cool;
    }
    
    /**
     * Rotate the image left by 90 degrees around its center.
     * <p>
     * Any pixels rotated in from off screen should be filled with FILL_VALUE. This function <i>does
     * not modify the original image</i>.
     *
     * @param originalImage the image to rotate left 90 degrees
     * @return the rotated image
     */
    public Color[][] rotateRight(final Color[][] originalImage) {

        Color[][] sq = new Color[Math.max(originalImage.length, originalImage[0].length)]
                [Math.max(originalImage.length, originalImage[0].length)];

        sq = fillWhite(sq);

            if (originalImage.length > originalImage[0].length) {
                    int x = (originalImage.length - originalImage[0].length) / 2;

                    for (int i = 0; i < originalImage.length; i++) {
                        for (int j = 0; j < originalImage[0].length; j++) {
                            sq[i][j + x] = originalImage[i][j];
                        }
                    }

                    Color[][] ro = new Color[sq.length][sq[0].length];
                    ro = rot(sq);

                    Color[][] out = new Color[originalImage.length][originalImage[0].length];

                    for (int i = 0; i < originalImage.length; i++) {
                        for (int j = 0; j < originalImage[0].length; j++) {
                            out[i][j] = Color.WHITE;
                        }
                    }

                    for (int i = 0; i < originalImage.length; i++) {
                        for (int j = 0; j < originalImage[0].length; j++) {
                            out[i][j] = ro[i][j + x];
                        }
                    }

                    return out;

              } else if (originalImage.length < originalImage[0].length) {
                    int x = (originalImage[0].length - originalImage.length) / 2;

                    for (int i = 0; i < originalImage.length; i++) {
                        for (int j = 0; j < originalImage[0].length; j++) {
                            sq[i + x][j] = originalImage[i][j];
                        }
                    }

                    Color[][] ro = new Color[sq.length][sq[0].length];
                    ro = rot(sq);

                    Color[][] out = new Color[originalImage.length][originalImage[0].length];

                    for (int i = 0; i < originalImage.length; i++) {
                        for (int j = 0; j < originalImage[0].length; j++) {
                            out[i][j] = Color.WHITE;
                        }
                    }

                    for (int i = 0; i < originalImage.length; i++) {
                        for (int j = 0; j < originalImage[0].length; j++) {
                            out[i][j] = ro[i + x][j];
                        }
                    }

                    return out;

              } else {
                  Color[][] arr = new Color[originalImage.length][originalImage[0].length];
                  arr = rot(originalImage);

                  return arr;

              }
    }

    /**
     * Rotate the image right by 90 degrees around its center.
     * <p>
     * Any pixels rotated in from off screen should be filled with FILL_VALUE. This function <i>does
     * not modify the original image</i>.
     *
     * @param originalImage the image to rotate right 90 degrees
     * @return the rotated image
     */
    public Color[][] rotateLeft(final Color[][] originalImage) {
    	return rotateRight(rotateRight(rotateRight(originalImage)));
    }
    
    /**
     * Flip the image on the vertical axis across its center.
     *
     * @param originalImage the image to flip vertically
     * @return the flipped image
     */
    public Color[][] flipVertical(final Color[][] originalImage) {
        Color[][] arr = new Color[originalImage.length][originalImage[0].length];
        for (int i = 0; i < originalImage.length; i++) {
            Color[] a = originalImage[i];
            for (int j = 0; j < originalImage[i].length; j++) {
                arr[i][originalImage[0].length - 1 - j] = a[j];
            }

        }


        return arr;
    }
    
    /**
     * Flip the image on the horizontal axis across its center.
     *
     * @param originalImage the image to flip horizontally
     * @return the flipped image
     */
    public Color[][] flipHorizontal(final Color[][] originalImage) {
        Color[][] arr = new Color[originalImage.length][originalImage[0].length];
        for (int i = 0; i < originalImage.length; i++) {
            Color[] a = originalImage[i];
            for (int j = 0; j < originalImage[i].length; j++) {
                arr[originalImage.length - i - 1][j] = a[j];
            }

        }

        return arr;
    }
    
    /**
     * Default amount to shift colors by. Not used by the testing suite, so feel free to change this
     * value.
     */
    public static final int DEFAULT_COLOR_SHIFT = 32;

    /**
     * Add red to the image.
     * <p>
     * This function <i>does not modify the original image</i>. It should also not generate any new
     * filled pixels.
     *
     * @param originalImage the image to add red to
     * @param amount the amount of red to add
     * @return the recolored image
     */
    public Color[][] moreRed(final Color[][] originalImage, final int amount) {
    	Color[][] arr = new Color[originalImage.length][originalImage[0].length];

        arr = fillWhite(arr);

        for (int i = 0; i < originalImage.length; i++) {
            for (int j = 0; j < originalImage[0].length; j++) {
            	if(originalImage[i][j].getRed() + amount <= 255) {
            		arr[i][j] = new Color(originalImage[i][j].getRed() + amount, 
            			originalImage[i][j].getGreen(), originalImage[i][j].getBlue());
            	} else {
            		arr[i][j] = new Color(255, originalImage[i][j].getGreen(), 
            				originalImage[i][j].getBlue());
            	}
            }
        }
        
        return arr;
    }
    
    /**
     * Subtract red from the image.
     * <p>
     * This function <i>does not modify the original image</i>. It should also not generate any new
     * filled pixels.
     *
     * @param originalImage the image to add red to
     * @param amount the amount of red to add
     * @return the recolored image
     */
    public Color[][] lessRed(final Color[][] originalImage, final int amount) {
    	Color[][] arr = new Color[originalImage.length][originalImage[0].length];

        arr = fillWhite(arr);

        for (int i = 0; i < originalImage.length; i++) {
            for (int j = 0; j < originalImage[0].length; j++) {
            	if(originalImage[i][j] != Color.WHITE) {
	            	if(originalImage[i][j].getRed() - amount >= 0) {
	            		arr[i][j] = new Color(originalImage[i][j].getRed() - amount, 
	            			originalImage[i][j].getGreen(), originalImage[i][j].getBlue());
	            	} else {
	            		arr[i][j] = new Color(0, originalImage[i][j].getGreen(), 
	            				originalImage[i][j].getBlue());
	            	}
            	}
            }
        }
        
        return arr;
    }
    
    /**
     * Add green to the image.
     * <p>
     * This function <i>does not modify the original image</i>. It should also not generate any new
     * filled pixels.
     *
     * @param originalImage the image to add red to
     * @param amount the amount of red to add
     * @return the recolored image
     */
    public Color[][] moreGreen(final Color[][] originalImage, final int amount) {
    	Color[][] arr = new Color[originalImage.length][originalImage[0].length];

        arr = fillWhite(arr);

        for (int i = 0; i < originalImage.length; i++) {
            for (int j = 0; j < originalImage[0].length; j++) {
            	if(originalImage[i][j].getGreen() + amount <= 255) {
            		arr[i][j] = new Color(originalImage[i][j].getRed(), 
            			originalImage[i][j].getGreen() + amount, originalImage[i][j].getBlue());
            	} else {
            		arr[i][j] = new Color(originalImage[i][j].getRed(), 255, 
            				originalImage[i][j].getBlue());
            	}
            }
        }
        
        return arr;
    }
    
    /**
     * Subtract green from the image.
     * <p>
     * This function <i>does not modify the original image</i>. It should also not generate any new
     * filled pixels.
     *
     * @param originalImage the image to add red to
     * @param amount the amount of red to add
     * @return the recolored image
     */
    public Color[][] lessGreen(final Color[][] originalImage, final int amount) {
    	Color[][] arr = new Color[originalImage.length][originalImage[0].length];

        arr = fillWhite(arr);

        for (int i = 0; i < originalImage.length; i++) {
            for (int j = 0; j < originalImage[0].length; j++) {
            	if(originalImage[i][j] != Color.WHITE) {
	            	if(originalImage[i][j].getGreen() - amount >= 0) {
	            		arr[i][j] = new Color(originalImage[i][j].getRed(), 
	            			originalImage[i][j].getGreen() - amount, originalImage[i][j].getBlue());
	            	} else {
	            		arr[i][j] = new Color(originalImage[i][j].getRed(), 0, 
	            				originalImage[i][j].getBlue());
	            	}
            	}
            }
        }
        
        return arr;
    }
    
    /**
     * Add blue to the image.
     * <p>
     * This function <i>does not modify the original image</i>. It should also not generate any new
     * filled pixels.
     *
     * @param originalImage the image to add red to
     * @param amount the amount of red to add
     * @return the recolored image
     */
    public Color[][] moreBlue(final Color[][] originalImage, final int amount) {
    	Color[][] arr = new Color[originalImage.length][originalImage[0].length];

        arr = fillWhite(arr);

        for (int i = 0; i < originalImage.length; i++) {
            for (int j = 0; j < originalImage[0].length; j++) {
            	if(originalImage[i][j].getBlue() + amount <= 255) {
            		arr[i][j] = new Color(originalImage[i][j].getRed(), 
            			originalImage[i][j].getGreen(), originalImage[i][j].getBlue() + amount);
            	} else {
            		arr[i][j] = new Color(originalImage[i][j].getRed(), 
            				originalImage[i][j].getGreen(), 255);
            	}
            }
        }
        
        return arr;
    }
    
    /**
     * Subtract blue from the image.
     * <p>
     * This function <i>does not modify the original image</i>. It should also not generate any new
     * filled pixels.
     *
     * @param originalImage the image to add red to
     * @param amount the amount of red to add
     * @return the recolored image
     */
    public Color[][] lessBlue(final Color[][] originalImage, final int amount) {
    	Color[][] arr = new Color[originalImage.length][originalImage[0].length];

        arr = fillWhite(arr);

        for (int i = 0; i < originalImage.length; i++) {
            for (int j = 0; j < originalImage[0].length; j++) {
            	if(originalImage[i][j] != Color.WHITE) {
	            	if(originalImage[i][j].getBlue() - amount >= 0) {
	            		arr[i][j] = new Color(originalImage[i][j].getRed(), 
	            			originalImage[i][j].getGreen(), originalImage[i][j].getBlue() + amount);
	            	} else {
	            		arr[i][j] = new Color(originalImage[i][j].getRed(), 
	            				originalImage[i][j].getGreen(), 0);
	            	}
            	}
            }
        }
        
        return arr;
    }
    
    /**
     * The default resize factor. Not used by the testing suite, so feel free to change this value.
     */
    public static final int DEFAULT_RESIZE_AMOUNT = 2;
    
    /**
     * Expand in the vertical axis around the image center.
     * <p>
     * An amount of 2 will result in an image that is twice its original height. An amount of 3 will
     * result in an image that's thrice its original height. Any pixels shrunk in from off
     * screen should be filled with FILL_VALUE. This function <i>does not modify the original
     * image</i>.
     *
     * @param originalImage the image to shrink
     * @param amount the factor by which the image's height is reduced
     * @return the shrunken image
     */
    public Color[][] expandVertical(final Color[][] originalImage, final int amount) {
        Color[][] arr = new Color[originalImage.length][originalImage[0].length];
        arr = fillWhite(arr);

        /* start from the center
         * do it half by half
         * make an arr that is first half (or for first loop, just reference first half)
         * then start from the last element in that array
         * and make the arr[i] that element "amount" number of times
         */

        //first half of array
        for (int i = 0; i < originalImage.length; i++) {
            int count = (originalImage[i].length / 2) - 1;
            for (int j = (originalImage[i].length / 2) - 1; j >= 0 && count >= 0; j--) {
                for (int k = 0; k < amount; k++) {
                    if (count >= 0) {
                        arr[i][count] = originalImage[i][j];
                        count--;
                    }
                }
            }
        }

        //second half of array
        for (int i = 0; i < originalImage.length; i++) {
            int count = originalImage[i].length / 2;
            for (int j = originalImage[i].length / 2; j < originalImage[i].length
                    && count < originalImage[i].length; j++) {
                for (int k = 0; k < amount; k++) {
                    if (count < originalImage[i].length) {
                        arr[i][count] = originalImage[i][j];
                        count++;
                    }
                }
            }
        }

        return arr;
    }
    
    /**
     * Expand in the horizontal axis around the image center.
     * <p>
     * An amount of 2 will result in an image that is twice its original width. An amount of 3 will
     * result in an image that's thrice its original width. Any pixels shrunk in from off
     * screen should be filled with FILL_VALUE. This function <i>does not modify the original
     * image</i>.
     *
     * @param originalImage the image to shrink
     * @param amount the factor by which the image's height is reduced
     * @return the shrunken image
     */
    public Color[][] expandHorizontal(final Color[][] originalImage, final int amount) {
        Color[][] arr = new Color[originalImage.length][originalImage[0].length];
        arr = fillWhite(arr);

        /* start from the center
         * do it half by half
         * make an arr that is first half (or for first loop, just reference first half)
         * then start from the last element in that array
         * and make the arr[i] that element "amount" number of times
         */

        //first half of array
        for (int i = 0; i < originalImage[0].length; i++) {
            int count = (originalImage.length / 2) - 1;
            for (int j = (originalImage.length / 2) - 1; j >= 0 && count >= 0; j--) {
                for (int k = 0; k < amount; k++) {
                    if (count >= 0) {
                        arr[count][i] = originalImage[j][i];
                        count--;
                    }
                }
            }
        }

        //second half of array
        for (int i = 0; i < originalImage[0].length; i++) {
            int count = originalImage.length / 2;
            for (int j = originalImage.length / 2; j < originalImage.length
                    && count < originalImage.length; j++) {
                for (int k = 0; k < amount; k++) {
                    if (count < originalImage.length) {
                        arr[count][i] = originalImage[j][i];
                        count++;
                    }
                }
            }
        }

        return arr;
    }


	private class MyDrawingPanel extends JPanel {

		// Not required, but gets rid of the serialVersionUID warning.  Google it, if desired.
		static final long serialVersionUID = 1234567890L;

		public void paintComponent(Graphics g) {

			g.setColor(Color.white);
			g.fillRect(2, 2, this.getWidth()-2, this.getHeight()-2);

			g.setColor(Color.lightGray);
			for (int x = 0; x < this.getWidth(); x += 20)
				g.drawLine(x, 0, x, this.getHeight());

			for (int y = 0; y < this.getHeight(); y += 20)
				g.drawLine(0, y, this.getWidth(), y);
			
			for (int i = 0; i < 20; i++) {
				for(int j = 0; j < 20; j++) {
					int x = i * 20;
					int y = j * 20;
					Color col = pic[i][j];
					g.setColor(col);
					g.fillRect(x + 1, y + 1, 19, 19);
				}
			}

		}
	}
	
	private class myMouse implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				Graphics g = drawingPanel.getGraphics();
				g.setColor(color);
				int x = e.getX() - (e.getX() % 20);
				int y = e.getY() - (e.getY() % 20);
				int xarr = x / 20;
				int yarr = y / 20;
				pic[xarr][yarr] = color;
				g.fillRect(x + 1, y + 1, 19, 19);
			}
			
			if(e.getButton() == MouseEvent.BUTTON3) {
				Graphics g = drawingPanel.getGraphics();
				g.setColor(Color.WHITE);
				int x = e.getX() - (e.getX() % 20);
				int y = e.getY() - (e.getY() % 20);
				int xarr = x / 20;
				int yarr = y / 20;
				pic[xarr][yarr] = Color.WHITE;
				g.fillRect(x + 1, y + 1, 19, 19);
			}
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class myMotion implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			if(e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK) {
				Graphics g = drawingPanel.getGraphics();
				g.setColor(color);
				int x = e.getX() - (e.getX() % 20);
				int y = e.getY() - (e.getY() % 20);
				g.fillRect(x + 1, y + 1, 19, 19);
				int xarr = x / 20;
				int yarr = y / 20;
				pic[xarr][yarr] = color;
			} else if(e.getModifiersEx() == MouseEvent.BUTTON3_DOWN_MASK) {
				Graphics g = drawingPanel.getGraphics();
				g.setColor(Color.WHITE);
				int x = e.getX() - (e.getX() % 20);
				int y = e.getY() - (e.getY() % 20);
				int xarr = x / 20;
				int yarr = y / 20;
				pic[xarr][yarr] = Color.WHITE;
				g.fillRect(x + 1, y + 1, 19, 19);
			}
			
		}
		
	

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		
	}
	
	private class myChange implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			//if(e.getSource() == chooseCol) {
				color = chooseCol.getColor();
				//MySketchPad pad = new MySketchPad(width, height);
				//guiHW3 lol = new guiHW3();
			//}
		}
	}
	
	private class myAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			System.out.println("Action -> " + e.getActionCommand());

			if (e.getActionCommand() != null) {

				if (e.getActionCommand().equals("Red"))
					color = Color.RED;
				else if (e.getActionCommand().equals("Green"))
					color = Color.GREEN;
				else if (e.getActionCommand().equals("Blue"))
					color = Color.BLUE;

				if (e.getActionCommand().equals("Reset")) {
					clearDraw();
				}
				if(e.getActionCommand().equals("Open")) {
					JFileChooser chooser = new JFileChooser(".");
					chooser.showOpenDialog(null);
					File fileChose = chooser.getSelectedFile();
					clearDraw();
					pic = read(fileChose);
					drawingPanel.repaint();
				}
				if(e.getActionCommand().equals("Save")) {
					JFileChooser chooser = new JFileChooser(".");
					chooser.showSaveDialog(null);
					File fileChose = chooser.getSelectedFile();
				}
				if (e.getActionCommand().equals("Clear")) {
					clearDraw();
				}
				if (e.getActionCommand().equals("Shift Up")) {
					update(shiftUp(pic, 4));
				}
				if (e.getActionCommand().equals("Shift Down")) {
					update(shiftDown(pic, 4));
				}
				if (e.getActionCommand().equals("Shift Right")) {
					update(shiftRight(pic, 4));
				}
				if (e.getActionCommand().equals("Shift Left")) {
					update(shiftLeft(pic, 4));
				}
				if (e.getActionCommand().equals("Flip Vertically")) {
					update(flipVertical(pic));
				}
				if (e.getActionCommand().equals("Flip Horizontally")) {
					update(flipHorizontal(pic));
				}
				if (e.getActionCommand().equals("Rotate Right")) {
					update(rotateRight(pic));
				}
				if (e.getActionCommand().equals("Rotate Left")) {
					update(rotateLeft(pic));
				}
				if (e.getActionCommand().equals("Expand Vertically")) {
					update(expandVertical(pic, 2));
				}
				if (e.getActionCommand().equals("Expand Horizontally")) {
					update(expandHorizontal(pic, 2));
				}
				if (e.getActionCommand().equals("More Red")) {
					update(moreRed(pic, 30));
				}
				if (e.getActionCommand().equals("Less Red")) {
					update(lessRed(pic, 30));
				}
				if (e.getActionCommand().equals("More Blue")) {
					update(moreBlue(pic, 30));
				}
				if (e.getActionCommand().equals("Less Blue")) {
					update(lessBlue(pic, 30));
				}
				if (e.getActionCommand().equals("More Green")) {
					update(moreGreen(pic, 30));
				}
				if (e.getActionCommand().equals("Less Green")) {
					update(lessGreen(pic, 30));
				}
			}

		}
	}
}
