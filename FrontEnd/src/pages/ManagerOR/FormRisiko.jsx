import React from "react";
import FormRisiko from "../../Components/Form/RegistrasiRisiko/FormRisiko";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class FormRegistrasiRisiko extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <FormRisiko />
            </SirioMainLayout>
        );
    }
};