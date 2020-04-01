import React from "react";
import FormTambah from "../../Components/Form/Employee/EmployeeFormTambah";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class FormTambahEmployee extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <FormTambah />
            </SirioMainLayout>
        );
    }
};