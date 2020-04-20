import React from "react";
import FormTambahRencanaPemeriksaan from "../../Components/Form/RencanaPemeriksaan/FormTambahRencanaPemeriksaan";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class TambahRencanaPemeriksaan extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <FormTambahRencanaPemeriksaan />
            </SirioMainLayout>
        );
    }
};