import React from "react";
import FormRisikoUbah from "../../Components/Form/RegistrasiRisiko/FormRisikoUbah";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class FormRegistrasiRisiko extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <FormRisikoUbah />
            </SirioMainLayout>
        );
    }
};