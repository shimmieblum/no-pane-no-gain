import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ViewerGUI provides the GUI for the project. It displays the property
 * and strings, and it listens to button clicks.

 */
public class ViewerGUI
{
    // fields:
    private JFrame frame;
    private JFrame statisticsFrame;
    private JPanel propertyPanel;
    private JLabel idLabel;

    private JTextField hostIDLabel;
    private JTextField hostNameLabel;
    private JTextField neighbourhoodLabel;
    private JTextField roomTypeLabel;
    private JTextField priceLabel;
    private JTextField minNightsLabel;
    private JTextArea descriptionLabel;

    private AirbnbListing currentProperty;
    private Viewer viewer;
    private boolean fixedSize;

    /**
     * Create a PropertyViewer and display its GUI on screen.
     */
    public ViewerGUI(Viewer viewer)
    {
        currentProperty = null;
        this.viewer = viewer;
        fixedSize = false;
        makeFrame();
        this.setPropertyViewSize(400, 250);
    }


    // ---- public view functions ----

    /**
     * Display a given property
     */
    public void showProperty(AirbnbListing property)
    {
        hostIDLabel.setText(property.getHost_id());
        hostNameLabel.setText(property.getHost_name());
        neighbourhoodLabel.setText(property.getNeighbourhood());
        roomTypeLabel.setText(property.getRoom_type());
        priceLabel.setText("£" + property.getPrice());
        minNightsLabel.setText(Integer.toString(property.getMinimumNights()));
        //descriptionLabel.setText(property.getDescription());
    }

    /**
     * Set a fixed size for the property display. If set, this size will be used for all properties.
     * If not set, the GUI will resize for each property.
     */
    public void setPropertyViewSize(int width, int height)
    {
        propertyPanel.setPreferredSize(new Dimension(width, height));
        frame.pack();
        fixedSize = true;
    }


    /**
     * Show the ID in the top of the screen.
     */
    public void showID(AirbnbListing property){
        idLabel.setText("Current Property ID:" + property.getId());
    }

    // ---- implementation of button functions ----

    /**
     * Called when the 'Next' button was clicked.
     */
    private void nextButton()
    {
        viewer.nextProperty();
    }

    /**
     * Called when the 'Previous' button was clicked.
     */
    private void previousButton()
    {
        viewer.previousProperty();
    }

    /**
     * Called when the 'View on MapScene' button was clicked.
     */
    private void viewOnMapsButton()
    {
        try{
            viewer.viewMap();
        }
        catch(Exception e){
            System.out.println("URL INVALID");
        }

    }

    /**
     * Called when the 'Statistics' button was clicked.
     */
    private void statisticsButton() {
        makeStatisticsFrame();

    }

    // ---- swing stuff to build the frame and all its components ----

    private void makeStatisticsFrame() {
        statisticsFrame = new JFrame("Statistics");

        JPanel contentPane = (JPanel)statisticsFrame.getContentPane();
        contentPane.setBorder(new EmptyBorder(6, 6, 6, 6));

        BoxLayout box = new BoxLayout(contentPane,1);
        contentPane.setLayout(box);
        //contentPane.setComponentOrientation(ComponentOrientation.);


        JLabel sizeLabel = new JLabel("Number of Properties Viewed: "+ viewer.getNumberOfPropertiesViewed());
        contentPane.add(sizeLabel);

        JLabel priceLabel = new JLabel("Average Property Price: "+ viewer.averagePropertyPrice());
        contentPane.add(priceLabel);

        frame.pack();
        // place the frame at the center of the screen and show
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        statisticsFrame.setLocation(d.width/2 - statisticsFrame.getWidth()/2, d.height/2 - statisticsFrame.getHeight()/2);
        statisticsFrame.setVisible(true);
    }

    /**
     * Create the Swing frame and its content.
     */
    private void makeFrame()
    {
        frame = new JFrame("Portfolio Viewer Application");
        JPanel contentPane = (JPanel)frame.getContentPane();
        contentPane.setBorder(new EmptyBorder(6, 6, 6, 6));

        // Specify the layout manager with nice spacing
        contentPane.setLayout(new BorderLayout(6, 6));

        // Create the property pane in the center
        propertyPanel = new JPanel();
        propertyPanel.setLayout(new GridLayout(6,2));

        propertyPanel.add(new JLabel("HostID: "));
        hostIDLabel = new JTextField("default");
        hostIDLabel.setEditable(false);
        propertyPanel.add(hostIDLabel);

        propertyPanel.add(new JLabel("Host Name: "));
        hostNameLabel = new JTextField("default");
        hostNameLabel.setEditable(false);
        propertyPanel.add(hostNameLabel);

        propertyPanel.add(new JLabel("Neighbourhood: "));
        neighbourhoodLabel = new JTextField("default");
        neighbourhoodLabel.setEditable(false);
        propertyPanel.add(neighbourhoodLabel);

        propertyPanel.add(new JLabel("Room type: "));
        roomTypeLabel = new JTextField("default");
        roomTypeLabel.setEditable(false);
        propertyPanel.add(roomTypeLabel);

        propertyPanel.add(new JLabel("Price: "));
        priceLabel = new JTextField("default");
        priceLabel.setEditable(false);
        propertyPanel.add(priceLabel);

        propertyPanel.add(new JLabel("Minimum nights: "));
        minNightsLabel = new JTextField("default");
        minNightsLabel.setEditable(false);
        propertyPanel.add(minNightsLabel);

        propertyPanel.setBorder(new EtchedBorder());
        contentPane.add(propertyPanel, BorderLayout.CENTER);

        // Create two labels at top and bottom for the file name and status message
        idLabel = new JLabel("default");
        contentPane.add(idLabel, BorderLayout.NORTH);



        // Create the toolbar with the buttons
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new GridLayout(0, 1));

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { nextButton(); }
        });
        toolbar.add(nextButton);

        JButton previousButton = new JButton("Previous");
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { previousButton(); }
        });
        toolbar.add(previousButton);

        JButton mapButton = new JButton("View Property on MapScene");
        mapButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { viewOnMapsButton(); }
        });
        toolbar.add(mapButton);

        JButton statisticsButton = new JButton("Statistics");
        statisticsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { statisticsButton(); }
        });
        toolbar.add(statisticsButton);


        // Add toolbar into panel with flow layout for spacing
        JPanel flow = new JPanel();
        flow.add(toolbar);

        contentPane.add(flow, BorderLayout.WEST);

        // building is done - arrange the components
        frame.pack();

        // place the frame at the center of the screen and show
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
        frame.setVisible(true);
    }
}
