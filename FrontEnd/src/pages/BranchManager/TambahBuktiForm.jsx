import React from "react";
import FormTambahBukti from "../../Components/Form/BuktiPelaksanaan/FormTambahBukti";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class TambahBuktiForm extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <FormTambahBukti />
            </SirioMainLayout>
        );
    }
};