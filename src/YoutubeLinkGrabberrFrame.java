import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author Kishu Agarwal
 */
public class YoutubeLinkGrabberrFrame extends javax.swing.JFrame
		implements YoutubeDownloader.DownloadListener, WindowStateListener {

	private YoutubeDownloader ytd;
	private String columns[] = { "Download", "Title", "Length" };
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton clipboardButton;
	private javax.swing.JCheckBox downloadAllCheckBox;
	private javax.swing.JButton downloadButton;
	private javax.swing.JTable downloadLinksTable;
	private javax.swing.JTextArea inputLinks;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JComboBox qualityComboBox;
	private javax.swing.JButton resetRutton;
	private javax.swing.JLabel statusLabel;
	private javax.swing.JLabel totalSizeLabel;

	/**
	 * Creates new form YoutubeLinkGrabberrFrame
	 */
	public YoutubeLinkGrabberrFrame() {
		System.setProperty("java.net.useSystemProxies", "true");
		initComponents();
		ytd = new YoutubeDownloader(downloadLinksTable, totalSizeLabel, this);
		Logger.init(statusLabel);
		Logger.setIsDebugLoggingEnabled(true);

	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/*
		 * Set the Nimbus look and feel
		 */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting
		// code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.
		 * html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(YoutubeLinkGrabberrFrame.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(YoutubeLinkGrabberrFrame.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(YoutubeLinkGrabberrFrame.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(YoutubeLinkGrabberrFrame.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/*
		 * Create and display the form
		 */
		QualityCodes.init();

		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new YoutubeLinkGrabberrFrame().setVisible(true);
			}
		});
	}

	@Override
	public void onDownloadComplete() {
		enableButtons();
	}

	@Override
	public void onDownloadError() {
		Logger.log("Download Error. Can't Continue;");
		enableButtons();
	}

	private void disableButtons() {
		downloadButton.setEnabled(false);
		qualityComboBox.setEnabled(false);
	}

	private void enableButtons() {
		downloadButton.setEnabled(true);
		qualityComboBox.setEnabled(true);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		downloadButton = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		inputLinks = new javax.swing.JTextArea();
		qualityComboBox = new javax.swing.JComboBox();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		totalSizeLabel = new javax.swing.JLabel();
		statusLabel = new javax.swing.JLabel();
		clipboardButton = new javax.swing.JButton();
		jScrollPane3 = new javax.swing.JScrollPane();
		String columns[] = { "ID", "Download", "Title", "Duration(min)" };
		Object rowData[][] = {};
		DefaultTableModel tableModel;
		tableModel = new DefaultTableModel(rowData, columns);

		downloadLinksTable = downloadLinksTable = new JTable(tableModel);
		;
		downloadAllCheckBox = new javax.swing.JCheckBox();
		resetRutton = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Youtube Link Grabber v1.0 -Kishu Agarwal ");

		downloadButton.setText("Grab Links");
		downloadButton.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				downloadButtonMouseClicked(evt);
			}
		});

		jLabel1.setText("Paste your links here");

		inputLinks.setColumns(20);
		inputLinks.setLineWrap(true);
		inputLinks.setRows(5);
		inputLinks.setWrapStyleWord(true);
		jScrollPane1.setViewportView(inputLinks);

		qualityComboBox.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "WebM 360P", "MP4 360P", "MP4 720P", "3GP 240P", "FLV 240p", "3GP 144p", "MP4 360P 3D",
						"MP4 240P 3D", "MP4 720P 3D", "MP4 1080P 3D", "WebM 360P VP8" }));
		qualityComboBox.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				qualityComboBoxActionPerformed(evt);
			}
		});

		jLabel2.setText("Total Size:");

		totalSizeLabel.setText("Pending");

		statusLabel.setText("Input Links and Press Grab Links");

		clipboardButton.setText("Copy to clipboard");
		clipboardButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				clipboardButtonActionPerformed(evt);
			}
		});

		downloadLinksTable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "ID", "Download", "Title", "Duration(min)" }) {
			Class[] types = new Class[] { java.lang.Integer.class, java.lang.Boolean.class, java.lang.String.class,
					java.lang.String.class };
			boolean[] canEdit = new boolean[] { false, true, false, false };

			@Override
			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jScrollPane3.setViewportView(downloadLinksTable);

		downloadAllCheckBox.setSelected(true);
		downloadAllCheckBox.setText("DownloadAll");
		downloadAllCheckBox.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				downloadAllCheckBoxActionPerformed(evt);
			}
		});

		resetRutton.setText("Reset");
		resetRutton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				resetRuttonActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(resetRutton).addGap(80, 80, 80))
						.addGroup(layout.createSequentialGroup().addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(downloadAllCheckBox)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addGroup(layout.createSequentialGroup()
												.addComponent(downloadButton, javax.swing.GroupLayout.PREFERRED_SIZE,
														146, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(57, 57, 57)
												.addComponent(qualityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE,
														111, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(46, 46, 46)
												.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 66,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(totalSizeLabel, javax.swing.GroupLayout.PREFERRED_SIZE,
														85, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE,
																49, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(clipboardButton,
																javax.swing.GroupLayout.PREFERRED_SIZE, 212,
																javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 807,
												Short.MAX_VALUE)
										.addComponent(jScrollPane3)))
								.addGap(0, 14, Short.MAX_VALUE)))));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127,
								javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
						.addComponent(clipboardButton, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(downloadButton, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
						.addComponent(jLabel3)
						.addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(totalSizeLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(qualityComboBox))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(downloadAllCheckBox)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 311,
						javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(resetRutton)).addContainerGap(19, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void qualityComboBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_qualityComboBoxActionPerformed
		ytd.setQuality((String) qualityComboBox.getSelectedItem());
	}// GEN-LAST:event_qualityComboBoxActionPerformed

	private void downloadButtonMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_downloadButtonMouseClicked
		disableButtons();
		ytd.setQuality((String) qualityComboBox.getSelectedItem());
		String[] input = inputLinks.getText().split("\\n");
		Logger.log("Starting new Session.");
		ytd.fetchLinks(input);
	}// GEN-LAST:event_downloadButtonMouseClicked

	private void clipboardButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clipboardButtonActionPerformed
		String links = ytd.getLinks();
		StringSelection selection = new StringSelection(links);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
		Logger.log("Links Copied to the clipboard.");
		try {
			Utils.save(links, "YTDLinks.txt");
			Logger.log("Links Also saved to the file \"YTDLinks.txt\".");
		} catch (IOException e) {
			System.err.println("Error saving the download links to the file :" + e.getMessage());
		}
	}// GEN-LAST:event_clipboardButtonActionPerformed

	private void downloadAllCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_downloadAllCheckBoxActionPerformed
		DefaultTableModel model = (DefaultTableModel) downloadLinksTable.getModel();
		int rows = model.getRowCount();
		for (int i = 0; i < rows; i++) {
			if (downloadAllCheckBox.isSelected())
				model.setValueAt(true, i, 1);
			else
				model.setValueAt(false, i, 1);
		}

	}// GEN-LAST:event_downloadAllCheckBoxActionPerformed

	private void resetRuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_resetRuttonActionPerformed
		ytd.cancelRunningTask();
		enableButtons();
		clipboardButton.setEnabled(true);
	}// GEN-LAST:event_resetRuttonActionPerformed

	@Override
	public void windowStateChanged(WindowEvent e) {
		if (e.getNewState() == WindowEvent.WINDOW_CLOSED) {
			Logger.close();
		}
	}
	// End of variables declaration//GEN-END:variables

}
