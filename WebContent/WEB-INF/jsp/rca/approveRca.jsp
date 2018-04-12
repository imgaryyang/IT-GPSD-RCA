	<div class="ibm-head">
		<p>
			<a class="ibm-common-overlay-close" href="#close">Close [x]</a>
		</p>
	</div>
	<div class="ibm-body">
		<div class="ibm-main">
			<div class="ibm-title">
				<h2>Approval Comments</h2>
			</div>
			<div class="ibm-overlay-rule">
				<hr />
			</div>
			<div class="ibm-container ibm-alternate">
				<div class="ibm-container-body">
					<p>
					<tr>
  			<td>
				<table class="ibm-results-table" width="100%" cellspacing="1"
					cellpadding="1" style="margin-bottom: 10px;"
					summary="The following table shows the labels of profile listing">
					<tbody>
					<thead>
						<form:textarea id="approvalComments" path="rcaForm.approvalComments" cols="70" rows="3"  style="font-size: 12px;"/>
					</thead>
					</tbody>
				</table>
			</td>
		</tr>
		  <tr>
                <div class="ibm-overlay-rule">
                    <hr/>
                </div>
                <div class="ibm-buttons-row">
                    <p>
                        <input value="Submit" id="approve-submit-btn" type="submit" name="ibm-submit"
                               class="ibm-btn-arrow-pri" onclick="approveRcaForm();" />
                        <span class="ibm-sep">&nbsp;</span>
                        <input value="Cancel" id="create-rca-cancel-btn" type="button" name="ibm-cancel"
                               class="ibm-btn-cancel-sec" onclick="ibmweb.overlay.hide('approve-rca-box');"/>
                    </p>
                </div>
            </tr>
					</p>
				</div>
			</div>
		</div>
	</div>
	<div class="ibm-footer"></div>
