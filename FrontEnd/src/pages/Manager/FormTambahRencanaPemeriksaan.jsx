import React from "react";
import FormTambahRencanaPemeriksaan from "../../Components/Form/RencanaPemeriksaan/FormTambahRencanaPemeriksaan";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class DaftarRencanaPemeriksaan extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <FormTambahRencanaPemeriksaan />
            </SirioMainLayout>
        );
    }
};