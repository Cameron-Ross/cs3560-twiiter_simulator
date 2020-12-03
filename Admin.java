import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class Admin extends JFrame implements ActionListener
{
	private static final Admin instance = new Admin();
	
	private JTree tree;
    private JLabel idLabel;
    private JLabel groupLabel;
    private JLabel pathLabel;
    private JButton addUser;
    private JButton addGroup;
    private JButton openUserView;
    private Entry selectedItem;
    private ArrayList<UserView> userViews;
    
	private Admin() 
	{
		userViews = new ArrayList<UserView>();
	}
	
	public static Admin getInstance()
	{
		return instance;
	}
	
	public void Launch()
	{
		SwingUtilities.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                createUI();
            }
        });
	}
	
	private void createUI()
	{
		makeTree();
		addButtons();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		pack();
		setVisible(true);
	}
	
	private void makeTree()
	{
		UserGroup rootEntry = (UserGroup) DataBase.getInsatnce().getRoot();
		tree = new JTree(addNodes(rootEntry));
		JScrollPane scroll = new JScrollPane(tree);
		add(scroll);
		
		tree.getSelectionModel().addTreeSelectionListener((TreeSelectionListener) new TreeSelectionListener() 
    	{
            public void valueChanged(TreeSelectionEvent e) 
            {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if(selectedNode != null)
                {
                	selectedItem = (Entry) selectedNode.getUserObject();
                    
                    if(selectedItem.getClass().getName().equals("UserGroup"))
                    {
                    	addUser.setEnabled(true);
                	    addGroup.setEnabled(true);
                	    openUserView.setEnabled(false);
                    }
                    else 
                    {
                    	addUser.setEnabled(false);
                	    addGroup.setEnabled(false);
                	    openUserView.setEnabled(true);
    				}
                    
                    idLabel.setText(" ID:" + selectedItem.getID());
                    pathLabel.setText(" Path:" + e.getPath().toString());
                    if(selectedItem == DataBase.getInsatnce().getRoot())
                    {
                    	groupLabel.setText(" Group ID: None");
                    }
                    else 
                    {
                    	groupLabel.setText(" Group ID: " + selectedItem.getParent().getID());
    				}
                }
                
            }
        });
	}
	
	private void addButtons()
	{
	    addUser = new JButton("Add User to Group");
	    addGroup = new JButton("Add Group to Group");
	    JButton showUserTotal = new JButton("Show User Total");
	    JButton showGroupTotal = new JButton("Show Group Total");
	    JButton showMessageTotal = new JButton("Show Total Tweets");
	    JButton showPositive = new JButton("Show Positive Percentage");
	    
	    JButton showInvalidIDs = new JButton("Show Invalid IDs");
	    JButton showLastUpdatedUser = new JButton("Show Last Updated User");
	    
	    openUserView = new JButton("Open User View");
	    idLabel = new JLabel("  ID");
        groupLabel = new JLabel("  Group ID");
        pathLabel = new JLabel("  Path");
        
	    addUser.addActionListener(this);
	    addGroup.addActionListener(this);
	    showUserTotal.addActionListener(this);
	    showGroupTotal.addActionListener(this);
	    showMessageTotal.addActionListener(this);
	    showPositive.addActionListener(this);
	    openUserView.addActionListener(this);
	    
	    showInvalidIDs.addActionListener(this);
	    showLastUpdatedUser.addActionListener(this);
	    
	    addUser.setEnabled(false);
	    addGroup.setEnabled(false);
	    openUserView.setEnabled(false);
	    
	    JPanel panel = new JPanel(new GridLayout(6,2));
	    
        panel.add(idLabel);
        panel.add(groupLabel);
        panel.add(openUserView);
        panel.add(addUser);
        panel.add(addGroup);
	    panel.add(showUserTotal);
	    panel.add(showGroupTotal);
	    panel.add(showMessageTotal);
	    panel.add(showPositive);
	    
	    panel.add(showInvalidIDs);
	    panel.add(showLastUpdatedUser);
	    
	    add(panel,BorderLayout.EAST);
        add(pathLabel, BorderLayout.PAGE_START);
	}
    
	public void actionPerformed(ActionEvent e) 
	{
		String buttonPressed = ((JButton) e.getSource()).getActionCommand();
		if(buttonPressed.equals("Add User to Group"))
		{
			String name = JOptionPane.showInputDialog("Enter User Name:");
			User newUser = new User(name, (UserGroup) selectedItem);
			updateTree();
		}
		else if(buttonPressed.equals("Add Group to Group")) 
		{
			String name = JOptionPane.showInputDialog("Enter Group Name:");
			UserGroup newGroup = new UserGroup(name, (UserGroup) selectedItem);
			updateTree();
		}
		else if(buttonPressed.equals("Open User View")) 
		{
			UserView userView = new UserView((User) selectedItem);
			userViews.add(userView);
			
		}
		else if(buttonPressed.equals("Find User to Follow")) 
		{
			UserView activeUserView = getActiveUserView();
			String name = JOptionPane.showInputDialog("Enter User Name to Follow:");
			User userToFollow = DataBase.getInsatnce().getUser(name);
			if(userToFollow == null)
			{
				JOptionPane.showMessageDialog(null,"User does not exist!");
			}
			else 
			{
				JOptionPane.showMessageDialog(null,"Now following the user!");
				activeUserView.getUser().followUser(userToFollow.getID());
				userToFollow.addFollower(activeUserView.getUser().getID());
				activeUserView.updateFollowing();
				
			}
		}
		else if(buttonPressed.equals("Show User Total")) 
		{
			JOptionPane.showMessageDialog(null,"There are " + DataBase.getInsatnce().getUserCount() + " users.");
		}
		else if(buttonPressed.equals("Show Group Total")) 
		{
			JOptionPane.showMessageDialog(null,"There are " + DataBase.getInsatnce().getGroupCount() + " groups.");
		}
		else if(buttonPressed.equals("Tweet")) 
		{
			UserView activeUserView = getActiveUserView();
			String txt = "" + activeUserView.getUser().getDisplayName() + ": " + activeUserView.getTweetText();
			activeUserView.getUser().post(txt);
			activeUserView.clearTweetText();
			updateNewsFeeds();
		}
		else if(buttonPressed.equals("Show Positive Percentage")) 
		{
			JOptionPane.showMessageDialog(null,DataBase.getInsatnce().getPositivePercentage() + " tweets are positive.");
		}
		else if(buttonPressed.equals("Show Total Tweets")) 
		{
			JOptionPane.showMessageDialog(null,"There are " + DataBase.getInsatnce().getTweetCount() + " tweets.");
		}
		else if(buttonPressed.equals("Show Invalid IDs")) 
		{
			ArrayList<String> invalidIDs = DataBase.getInsatnce().getInvalidIDs();
			String message = "";
			if(invalidIDs.size() == 0)
			{
				message = "All IDs are valid.";
			}
			else 
			{
				for(String id : invalidIDs)
				{
					message += (id + " is not a valid ID!\n");
				}
			}
			JOptionPane.showMessageDialog(null,message);
		}
		else if(buttonPressed.equals("Show Last Updated User")) 
		{
			User user = DataBase.getInsatnce().getLastUpdatedUser();
			String name = user.getDisplayName();
			long time = user.getLastUpdateTime();
			JOptionPane.showMessageDialog(null,"The last updated user was " + name + " at: " + formatDate(time) + "." );
		}
		
	}
	
	private DefaultMutableTreeNode addNodes(UserGroup group)
	{
		if(group == null) return null;
		
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(group);
		
		for(Entry e : group.getEntries()) 
		{
			DefaultMutableTreeNode node;
			if(e.getClass().getName().equals("UserGroup"))
			{
				node = addNodes((UserGroup) e);
			}
			else 
			{
				node = new DefaultMutableTreeNode(e);
			}
			rootNode.add(node);
		}
		
		return rootNode;
	}

	private void updateTree()
	{
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
		UserGroup rootEntry = (UserGroup) DataBase.getInsatnce().getRoot();
		model.setRoot(addNodes(rootEntry));
		model.reload(root);
	}

	private UserView getActiveUserView()
	{
		for(UserView v : userViews)
		{
			if(v.frameActivce())
			{
				return v;
			}
		}
		return null;
	}
	

	private void updateNewsFeeds()
	{
		for(UserView view : userViews)
		{
			view.updateFeed();
		}
	}
	
	private String formatDate(long time)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy hh:mm:ss a");    
		Date resultdate = new Date(time);
		return sdf.format(resultdate);
	}

}
