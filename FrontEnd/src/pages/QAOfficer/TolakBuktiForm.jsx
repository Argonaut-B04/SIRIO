import React from "react";
import FormTolakBukti from "../../Components/Form/BuktiPelaksanaan/FormTolakBukti";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class TolakBuktiForm extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <FormTolakBukti />
            </SirioMainLayout>
        );
    }
};