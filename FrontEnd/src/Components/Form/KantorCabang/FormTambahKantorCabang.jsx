import React from 'react';
import SirioForm from '../../Form/SirioForm';
import SirioButton from '../../Button/SirioButton';
import KantorCabangService from '../../../Services/KantorCabangService';
import EmployeeService from '../../../Services/EmployeeService';
import SirioMessageButton from '../../Button/ActionButton/SirioMessageButton';

/**
 * Kelas untuk membuat form demo
 */
export default class FormTambahKantorCabang extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            namaKantorCabang: "",
            idPemilik: "",
            area: "",
            regional: "",
            kunjunganAudit: "",
            employeeOptionList: []
        }

        this.renderEmployeeOption = this.renderEmployeeOption.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
    }

    componentDidMount() {
        this.renderEmployeeOption();
    }

    async renderEmployeeOption() {
        const response = await EmployeeService.getEmployeeList();

        const employeeOptionList = response.data.result.map(employee => {
            return (
                {
                    label: employee.nama,
                    value: employee.idEmployee
                }
            )
        });

        this.setState({
            employeeOptionList: employeeOptionList
        })
    }

    // Fungsi untuk mengubah state ketika isi dari input diubah
    // Fungsi ini wajib ada jika membuat form
    handleChange(event) {
        this.setState(
            {
                [event.target.name]
                    : event.target.value
            }
        )
    }

    // Fungsi untuk mengubah state ketika isi dropdown diubah
    // Fungsi unu wajib ada jika membuat field tipe select
    handleSelectChange(name, event) {
        this.setState(
            {
                [name]
                    : event.value
            }
        )
    }

    // Fungsi yang akan dijalankan ketika user submit
    // Umumnya akan digunakan untuk memanggil service komunikasi ke backend
    handleSubmit(event) {
        event.preventDefault();
        const kantorCabang = {
            namaKantorCabang: this.state.namaKantorCabang,
            idPemilik: this.state.idPemilik,
            area: this.state.area,
            regional: this.state.regional,
            kunjunganAudit: this.state.kunjunganAudit
        }
        KantorCabangService.addKantorCabang(kantorCabang)
        // console.log(event)
        
        .then( () => window.location.href = "/administrator/kantorCabang");
    }

    // Fungsi yang akan mengembalikan definisi tiap field pada form
    // Setiap objek {} pada List [] akan menjadi 1 field
    // untuk informasi lebih lengkap, cek SirioForm
    inputDefinition() {
        return (
            [
                {
                    label: "Nama Point*",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "namaKantorCabang",
                    value: this.state.namaKantorCabang,
                    placeholder: "Masukan nama point"
                }, {
                    label: "Branch Manger*",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    name: "idPemilik",
                    value: this.state.idPemilik,
                    optionList: this.state.employeeOptionList
                },{
                    label: "Area*",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "area",
                    value: this.state.area,
                    placeholder: "Masukan nama area"
                },{
                    label: "Regional*",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "regional",
                    value: this.state.regional,
                    placeholder: "Masukan nama regional"
                },{
                    label: "Kunjungan Audit*",
                    handleChange: this.handleChange,
                    type: "checkbox",
                    name: "kunjunganAudit",
                    value: this.state.kunjunganAudit,
                }


            ]
        )
    }

    submitButton() {
        return (
            <div>
                <SirioButton purple recommended
                    classes="mx-2"
                    onClick={(event)  => this.handleSubmit(event)}>
                    Simpan
                </SirioButton>
                <SirioButton purple
                    onClick={() => window.location.href = "/administrator/kantorCabang"}>
                    Batal
                </SirioButton>
            </div>
        )
    }

    // Fungsi render SirioForm
    render() {
        return (
            <>
                <SirioForm
                    title="Form Tambah Kantor Cabang"
                    inputDefinition={this.inputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />
           
                {this.state.changeComplete &&
                    <SirioMessageButton
                        show
                        classes="d-none"
                        modalTitle="Tenggat Waktu berhasil Disimpan"
                        customConfirmText="Kembali"
                        onClick={this.endNotification}
                    />
                }
             </>
        );
    }
}