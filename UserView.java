import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class UserView 
{
	private User user;
	private JFrame frame;
	private JLabel following;
	private JLabel feed;
	private JLabel info;
	private JTextField tweetField;
	
	public UserView(User user)
	{
		this.user = user;
		SwingUtilities.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
            	createUserView();
            }
        });
	}
	
	public boolean frameActivce()
	{
		return frame.isActive();
	}
	
	public User getUser()
	{
		return user;
	}
	
	public String getTweetText()
	{
		return tweetField.getText();
	}
	
	public void clearTweetText()
	{
		tweetField.setText("");
	}
	
	public void updateFollowing()
	{
		String followText = "<html>Following:" + formatFollowing() + "</html>";
		following.setText(followText);
	}
	
	public void updateFeed()
	{
		String feedText = "<html>Feed:" + formatFeed() + "</html>";
		feed.setText(feedText);
		info.setText(getInfoText());
	}
	
	private void createUserView()
	{
		frame = new JFrame(user.getDisplayName() + "'s Profile");
		addUserViewContent(user, frame);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		frame.pack();
		frame.setVisible(true);
	}
	
	private void addUserViewContent(User user, JFrame frame)
	{
		frame.setLayout(new GridLayout(4,1,10,10));
		
		// follow section
		JPanel panel1 = new JPanel();
		JButton follow = new JButton("Find User to Follow");
		
		// id section
		// JLabel myID = new JLabel(idText);
		
		// updated section
		info = new JLabel(getInfoText());
		
		
		follow.addActionListener(Admin.getInstance());
		// panel1.add(myID);
		panel1.add(info);
		panel1.add(follow);
		frame.add(panel1);
		
		// follow list
		String followText = "<html>Following:" + formatFollowing() + "</html>";
		following = new JLabel(followText);
		frame.add(following, BorderLayout.CENTER);
		
		// tweet section
		JPanel panel2 = new JPanel();
		JButton tweet = new JButton("Tweet");
		tweetField = new JTextField("Type Tweet",30);
		tweet.addActionListener(Admin.getInstance());
		panel2.add(tweetField);
		panel2.add(tweet);
		frame.add(panel2);
		
		// feed section
		feed = new JLabel();
		String feedText = "<html>Feed:" + formatFeed() + "</html>";
		feed.setText(feedText);
		JScrollPane scroll = new JScrollPane(feed);
		frame.add(scroll, BorderLayout.EAST);

	}
	
	private String formatFollowing()
	{
		String result = "";
		for(int i = 0; i < user.getFollowing().size(); i++)
		{
			result += ("<br>" + (i+1) + ") " + DataBase.getInsatnce().getEntry(user.getFollowing().get(i)).getDisplayName()); 
		}
		return result;
	}
	
	private String formatFeed()
	{
		String result = "";
		for(int i = 0; i < user.getFeed().size(); i++)
		{
			result += ("<br>" + (i+1) + ") " + user.getFeed().get(i)); 
		}
		return result;
	}
	
	private String getInfoText()
	{
		String idText = "<html>My Name: " + user.getDisplayName() + "<br>My ID: " + user.getID() + "<br>";
		
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy hh:mm:ss a");
		String created = sdf.format(new Date(user.getCreationTime())); 
		String updated = sdf.format(new Date(user.getLastUpdateTime())); 
		String updateText = "Created: " + created + "<br>Last Updated: " + updated + "</html>";
		
		return idText + updateText;
	}
	
	
	
}
