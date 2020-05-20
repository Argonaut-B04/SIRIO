import React from "react";
import FormUbahSOP from "../../Components/Form/SOP/FormUbahSOP";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class UbahSOP extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <FormUbahSOP />
            </SirioMainLayout>
        );
    }
};