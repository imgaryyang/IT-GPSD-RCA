
package com.gps.vo.helper;

import java.io.Serializable;

/**
 * This class is a helper vo for UI.

 */

public class RcaFormActionsHelper implements Serializable{

    private String approvalComments;
    private String rejectionComments;
    private String cancellationComments;
    private String reopenComments;

    public String getApprovalComments() {
        return approvalComments;
    }

    public void setApprovalComments(String approvalComments) {
        this.approvalComments = approvalComments;
    }

    public String getRejectionComments() {
        return rejectionComments;
    }

    public void setRejectionComments(String rejectionComments) {
        this.rejectionComments = rejectionComments;
    }

    public String getCancellationComments() {
        return cancellationComments;
    }

    public void setCancellationComments(String cancellationComments) {
        this.cancellationComments = cancellationComments;
    }


    public String getReopenComments() {
        return reopenComments;
    }

    public void setReopenComments(String reopenComments) {
        this.reopenComments = reopenComments;
    }
}
