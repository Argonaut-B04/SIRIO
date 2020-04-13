import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import EmployeeService from '../../../Services/EmployeeService';
import KantorCabangService from '../../../Services/KantorCabangService';
import { Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import RencanaPemeriksaanService from '../../../Services/RencanaPemeriksaanService';

/**
 * Kelas untuk membuat form demo
 */
class FormUbahRencana extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            namaRencana: "",
            linkMajelis: "",
            status: 1,
            kantorOptionList: [],
            employeeOptionList: [],
            tugasPemeriksaanList: [{
                kantorCabang: "",
                idQA: "",
                tanggalMulai: "",
                tanggalSelesai: "",
            }]
        }

        this.handleChange = this.handleChange.bind(this);
        this.innerInputDefinition = this.innerInputDefinition.bind(this);
        this.handleMultipleSelectChange = this.handleMultipleSelectChange.bind(this);
        this.handleMultipleChange = this.handleMultipleChange.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.renderEmployeeOption = this.renderEmployeeOption.bind(this);
        this.renderKantorOption = this.renderKantorOption.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

        this.renderDataRencana = this.renderDataRencana.bind(this);
    }

    componentDidMount() {
        this.renderEmployeeOption();
        this.renderKantorOption();
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/manager/rencanaPemeriksaan",
                state: {
                    addSuccess: true
                }
            }} />
        }
    };
    
    async renderDataRencana() {
        const response = await RencanaPemeriksaanService.getRencanaPemeriksaanDetail(this.props.location.state.id);

        this.setState({
            id: response.data.result.id,
            namaRencana: response.data.result.namaRencana,
            linkMajelis: response.data.result.linkMajelis,
            status: response.data.result.status,
            daftarTugasPemeriksaan: response.data.result.daftarTugasPemeriksaan.map(tugas => {
                return (
                    {
                        kantorCabang: tugas.kantorCabang,
                        idQA: tugas.idQA,
                        tanggalMulai: tugas.tanggalMulai,
                        tanggalSelesai: tugas.tanggalSelesai
                    }
                )
            })
        })
    }

    handleMultipleChange(event, index) {
        console.log(event);
        console.log(index);
        const tugasPemeriksaanList = this.state.tugasPemeriksaanList
        tugasPemeriksaanList[index][event.target.name] = event.target.value;

        this.setState(
            {
                tugasPemeriksaanList: tugasPemeriksaanList
            }
        )
    }

    // Fungsi untuk mengubah state ketika isi dropdown diubah
    // Fungsi ini wajib ada jika membuat field tipe select
    handleMultipleSelectChange(name, target, index) {
        // const tugasPemeriksaanList = this.state.tugasPemeriksaanList
        // tugasPemeriksaanList[index][event.target.name] = event.target.value;
        console.log(name);
        console.log(target);
        console.log(index);
        const formList = this.state.tugasPemeriksaanList;
        formList[index][name] = target.value;
        this.setState(
            {
                //tugasPemeriksaanList: tugasPemeriksaanList,
                tugasPemeriksaanList: formList
            }
        )
    }

    async renderEmployeeOption() {
        const response = await EmployeeService.getAllQAOfficer();

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

    async renderKantorOption() {
        const response = await KantorCabangService.getKantorCabangList();

        const kantorOptionList = response.data.result.map(kantorCabang => {
            return (
                {
                    label: kantorCabang.namaKantor,
                    value: kantorCabang.idKantor
                }
            )
        });

        this.setState({
            kantorOptionList: kantorOptionList
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

    // Fungsi yang akan dijalankan ketika user submit
    // Umumnya akan digunakan untuk memanggil service komunikasi ke backend
    // Fungsi yang akan dijalankan ketika user submit
    // Umumnya akan digunakan untuk memanggil service komunikasi ke backend
    handleSubmit(event, nama) {
        if(nama == "simpan"){
            event.preventDefault();
            const rencanaPemeriksaan = {
                namaRencana: this.state.namaRencana,
                linkMajelis: this.state.linkMajelis,
                status: 2,
                tugasPemeriksaanList: [{
                    kantorCabang: this.state.kantorCabang,
                    idQA: this.state.idQA,
                    tanggalMulai: this.state.tanggalMulai,
                    tanggalSelesai: this.state.tanggalSelesai,
                }]
            }
            RencanaPemeriksaanService.editRencanaPemeriksaan(rencanaPemeriksaan)
            .then(() => this.setRedirect());
        }
        else if (nama == "draft"){
            event.preventDefault();
            const rencanaPemeriksaan = {
                namaRencana: this.state.namaRencana,
                linkMajelis: this.state.linkMajelis,
                status: this.state.status,
                tugasPemeriksaanList: [{
                    kantorCabang: this.state.kantorCabang,
                    idQA: this.state.idQA,
                    tanggalMulai: this.state.tanggalMulai,
                    tanggalSelesai: this.state.tanggalSelesai,
                }]
            }
            RencanaPemeriksaanService.editRencanaPemeriksaan(rencanaPemeriksaan)
            .then(() => this.setRedirect());
        }

    }

    fullComponentInside() {
        const forms = [];
        for (let i = 0; i < this.state.tugasPemeriksaanList.length; i++) {
            forms.push(
                <SirioForm
                    subtitle="Tugas Pemeriksaan"
                    key={i}
                    childForm
                    id={i}
                    inputDefinition={this.innerInputDefinition(i)}
                    footerButton={this.childFooterButton(i)}
                />
            )
        }
        return (
            <>
                {forms.map(form => form)}
            </>
        )
    }

    outerInputDefinition() {
        return (
            [
                {
                    label: "Nama Rencana*",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "namaRencana",
                    value: this.state.namaRencana,
                    placeholder: "Masukan nama rencana"
                }, {
                    label: "Link Pemeriksaan*",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "linkMajelis",
                    value: this.state.linkMajelis,
                    placeholder: "Masukan link pemeriksaan"
                },{
                    fullComponent:
                        this.tambahTugasButton
                }, {
                    fullComponent:
                        this.fullComponentInside()
                } 

            ]
        )
    }

    tambahTugasButton = (
        <SirioButton blue recommended
            classes="mr-3"
            onClick={() => this.addForm()}
            type="button"
        >
            Tambah Tugas
        </SirioButton>
    )

    innerInputDefinition(index) {
        return (
            [
                {
                    label: "Kantor Cabang*",
                    handleChange: this.handleMultipleSelectChange,
                    index: index,
                    type: "select",
                    name: "kantorCabang",
                    value: this.state.tugasPemeriksaanList[index].kantorCabang,
                    optionList: this.state.kantorOptionList
                }, {
                    label: "QA Officer*",
                    handleChange: this.handleMultipleSelectChange,
                    index: index,
                    type: "select",
                    name: "idQA",
                    value: this.state.tugasPemeriksaanList[index].idQA,
                    optionList: this.state.employeeOptionList
                }, {
                    label: "Tanggal Mulai*",
                    handleChange: this.handleMultipleChange,
                    index: index,
                    type: "date",
                    name: "tanggalMulai",
                    value: this.state.tugasPemeriksaanList[index].tanggalMulai,
                    placeholder: "Masukan tanggal mulai"
                }, {
                    label: "Tanggal Selesai*",
                    handleChange: this.handleMultipleChange,
                    index: index,
                    type: "date",
                    name: "tanggalSelesai",
                    value: this.state.tugasPemeriksaanList[index].tanggalSelesai,
                    placeholder: "Masukan tanggal selesai"
                }

            ]
        )
    }

    submitButton() {
        return (
            <div>
                <SirioButton purple recommended
                    classes="mx-1"
                    onClick={(event) => this.handleSubmit(event,"simpan")}>
                    Simpan
                </SirioButton>
                <SirioButton purple
                    classes="mx-1"
                    onClick={(event) => this.handleSubmit(event,"draft")}>
                    Draft
                </SirioButton>
                <SirioButton purple
                    classes="mx-1"
                    onClick={() => window.location.href = "/manager/rencanaPemeriksaan"}>
                    Batal
                </SirioButton>
            </div>
        )
    }

    deleteItem(array, index) {
        const toReturn = []
        for (var row in array) {
            // eslint-disable-next-line
            if (row != index) {
                toReturn.push(array[row]);
            }
        }
        return toReturn;
    }


    deleteChildForm(index) {
        const tugasPemeriksaanList = this.state.tugasPemeriksaanList
        const newtugasPemeriksaanList = this.deleteItem(tugasPemeriksaanList, index)

        this.setState(
            {
                tugasPemeriksaanList: newtugasPemeriksaanList
            }
        )
    }

    childFooterButton(index) {
        return (
            <div>
                <SirioButton
                    red
                    type="button"
                    onClick={() => this.deleteChildForm(index)}
                >
                    Hapus
                </SirioButton>
            </div>
        )
    }

    addForm() {
        const tugasPemeriksaanList = this.state.tugasPemeriksaanList
        tugasPemeriksaanList.push({});

        this.setState(
            {
                tugasPemeriksaanList: tugasPemeriksaanList
            }
        )
    }

    render() {
        return (
            <>
                {this.renderRedirect()}
                <SirioForm
                    title="Form Ubah Rencana Pemeriksaan"
                    inputDefinition={this.outerInputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />

            </>

        );
    }
}

export default withRouter(FormUbahRencana);