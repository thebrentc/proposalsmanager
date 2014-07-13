package proposalsManager;

import java.awt.Color;
import java.util.Iterator;
import javax.swing.DefaultListModel;

/**
 *
 * @author brent
 */
public class ProposalReviewsGUI extends javax.swing.JFrame {
    
    Proposal proposal;

    /**
     * Creates new form ProposalsGUI
     */
    public ProposalReviewsGUI(Proposal proposal) {
        initComponents();
        this.proposal =  proposal;
        updateProposalInfo();
        updateProposalApplicableReviewsList();
        
    }
    
    public void updateProposalInfo()
    {
        this.proposalInfo.setText(this.proposal.toString());
    }
    
    public void updateProposalApplicableReviewsList() {
        
        DefaultListModel listModel = new DefaultListModel();
        
        //for (<ProposalRequiredReview proposalRequiredReview : this.proposal.proposalRequiredReviews) {  
                

        for (int c = 0; c < this.proposal.getProposalApplicableReviews().size(); c++) {                    
            listModel.addElement(this.proposal.getProposalApplicableReviews().get(c).toString());
        }
        jProposalRequiredReviews.setModel(listModel);
        
        jProposalRequiredReviews.setSelectedIndex(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        proposalInfo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jProposalRequiredReviews = new javax.swing.JList();
        jButtonReview = new javax.swing.JButton();
        jProposalRequiredReviewsRequiredLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(600, 346));
        jPanel1.setVerifyInputWhenFocusTarget(false);

        jLabel2.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        jLabel2.setText("Proposal");

        jLabel7.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        jLabel7.setText("Proposal applicable reviews");

        proposalInfo.setText("proposalInfo");

        jProposalRequiredReviews.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jProposalRequiredReviews);

        jButtonReview.setLabel("review");
        jButtonReview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReviewActionPerformed(evt);
            }
        });

        jProposalRequiredReviewsRequiredLabel.setText("*");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7)
                            .addComponent(proposalInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 681, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonReview))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProposalRequiredReviewsRequiredLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(proposalInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jProposalRequiredReviewsRequiredLabel))
                .addGap(34, 34, 34)
                .addComponent(jButtonReview)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 705, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonReviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReviewActionPerformed

        if (jProposalRequiredReviews.getSelectedValue() == null) {
            jProposalRequiredReviewsRequiredLabel.setForeground(Color.red);            
            return;
        }        
        int index = jProposalRequiredReviews.getSelectedIndex();

        ProposalApplicableReview proposalApplicableReview = proposal.getProposalApplicableReviews().get(index);
        System.out.println(proposalApplicableReview.getClass());
        System.out.println(index + this.proposal.toString() + proposalApplicableReview.toString());
        ProposalReviewGUI proposalReviewGUI = new ProposalReviewGUI(this, this.proposal, proposalApplicableReview);
        proposalReviewGUI.setVisible(true);
    }//GEN-LAST:event_jButtonReviewActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonReview;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JList jProposalRequiredReviews;
    private javax.swing.JLabel jProposalRequiredReviewsRequiredLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel proposalInfo;
    // End of variables declaration//GEN-END:variables
}
