import React from "react";
import FormTambahSOP from "../../Components/Form/SOP/FormTambahSOP";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class TambahSOP extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <FormTambahSOP />
            </SirioMainLayout>
        );
    }
};