import React from "react";
import FormUbahBukti from "../../Components/Form/BuktiPelaksanaan/FormUbahBukti";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class UbahBuktiForm extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <FormUbahBukti />
            </SirioMainLayout>
        );
    }
};