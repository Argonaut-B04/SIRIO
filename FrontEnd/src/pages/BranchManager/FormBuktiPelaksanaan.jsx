import React from "react";
import FormBuktiBM from "../../Components/Form/BuktiPelaksanaan/FormBukti";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class FormBuktiPelaksanaan extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <FormBuktiBM />
            </SirioMainLayout>
        );
    }
};