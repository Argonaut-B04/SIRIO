import React from "react";
import FormFeedback from "../../Components/Form/BuktiPelaksanaan/FormFeedbackBukti";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class FormFeedbackBukti extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <FormFeedback />
            </SirioMainLayout>
        );
    }
};