import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import SirioWarningButton from "../../Button/ActionButton/SirioWarningButton";
import EmployeeService from '../../../Services/EmployeeService';
import KantorCabangService from '../../../Services/KantorCabangService';
import TugasPemeriksaanService from '../../../Services/TugasPemeriksaanService';
import { NavLink, Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import RencanaPemeriksaanService from '../../../Services/RencanaPemeriksaanService';
import moment from 'moment';

/**
 * Kelas untuk membuat form demo
 */
class FormUbahRencana extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            redirect: false,
            id: "",
            namaRencana: "",
            linkMajelis: "",
            status: "",
            kantorOptionList: [],
            employeeOptionList: [],
            daftarTugasPemeriksaan: [{
                kantorCabang: "",
                idQA: "",
                tanggalMulai: "",
                tanggalSelesai: "",
            }]
        }

        this.handleChange = this.handleChange.bind(this);
        this.innerInputDefinition = this.innerInputDefinition.bind(this);
        this.handleMultipleSelectChange = this.handleMultipleSelectChange.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.renderDataRencana = this.renderDataRencana.bind(this);
        this.submitableSimpan = this.submitableSimpan.bind(this);
        this.submitable = this.submitable.bind(this);
    }

    componentDidMount() {
        this.renderDataRencana();
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/rencana-pemeriksaan",
                state: {
                    editSuccess: true
                }
            }} />
        }
    };

    async renderDataRencana() {
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengambil data dari server");

        const responseEmployee = await EmployeeService.getAllQAOfficer();

        const employeeOptionList = responseEmployee.data.result.map(employee => {
            return (
                {
                    label: employee.nama,
                    value: employee.idEmployee
                }
            )
        });

        const responseKantor = await KantorCabangService.getKantorCabangList();

        const kantorOptionList = responseKantor.data.result.map(kantorCabang => {
            return (
                {
                    label: kantorCabang.namaKantor,
                    value: kantorCabang.idKantor
                }
            )
        });

        const response = await RencanaPemeriksaanService.getRencanaPemeriksaanDetail(this.props.location.state.id);
        const result = response.data.result;

        for (var i = 0; i < result.daftarTugasPemeriksaan.length; i++) {
            const tanggalMulai = result.daftarTugasPemeriksaan[i].tanggalMulai;
            const tanggalSelesai = result.daftarTugasPemeriksaan[i].tanggalSelesai;
            result.daftarTugasPemeriksaan[i].tanggalMulai = tanggalMulai.split(" ")[0];
            result.daftarTugasPemeriksaan[i].tanggalSelesai = tanggalSelesai.split(" ")[0];
        }

        this.props.changeLoadingBody("Menampilkan data");

        this.setState({
            employeeOptionList: employeeOptionList,
            kantorOptionList: kantorOptionList,
            id: response.data.result.id,
            namaRencana: response.data.result.namaRencana,
            linkMajelis: response.data.result.linkMajelis,
            status: response.data.result.status,
            daftarTugasPemeriksaan: result.daftarTugasPemeriksaan,
            errorLink: "",
            errorNama: ""
        }, this.props.contentFinishLoading())

    }

    handleSelectChange(event, index) {
        const daftarTugasPemeriksaan = this.state.daftarTugasPemeriksaan
        daftarTugasPemeriksaan[index][event.target.name] = event.target.value;

        if (event.target.name === "tanggalMulai") {
            daftarTugasPemeriksaan[index].tanggalSelesai = ""
        }

        this.setState(
            {
                daftarTugasPemeriksaan: daftarTugasPemeriksaan
            }
        )
    }

    // Fungsi untuk mengubah state ketika isi dropdown diubah
    // Fungsi ini wajib ada jika membuat field tipe select
    handleMultipleSelectChange(name, target, index) {
        const formList = this.state.daftarTugasPemeriksaan;
        formList[index][name] = target.value;
        this.setState(
            {
                daftarTugasPemeriksaan: formList
            }
        )
    }

    validateNama(fokusNama) {
        var errorNama = "";
        var letterOnly = /^[a-zA-Z\s]*$/;
        if (fokusNama === null || fokusNama === "") {
            errorNama = "Nama rencana harus diisi";
        } else if (!fokusNama.match(letterOnly)) {
            errorNama = "Nama hanya boleh mengandung huruf";
        } else if (fokusNama.length > 25) {
            errorNama = "Nama rencana tidak boleh lebih dari 25 karakter";
        }

        this.setState({
            errorNama: errorNama
        })

    }

    validateLink(fokusLink) {
        var errorLink = "";

        // eslint-disable-next-line
        var link = /^(?:http(s)?:\/\/)?[\w.-]+(?:\.[\w\.-]+)+[\w\-\._~:/?#[\]@!\$&'\(\)\*\+,;=.%]+$/;
        if (fokusLink === null || fokusLink === "") {
            errorLink = "Lampiran wajib diisi";
        } else if (!fokusLink.match(link)) {
            errorLink = "Lampiran tidak sesuai format link url";
        } else if (fokusLink.length > 255) {
            errorLink = "Link tidak boleh lebih dari 255 karakter";
        }

        this.setState({
            errorLink: errorLink
        })

    }

    submitable() {
        return this.state.errorNama === "" &&
            this.state.errorLink === "" &&
            (this.state.namaRencana !== null && this.state.namaRencana !== "") &&
            (this.state.linkMajelis !== null && this.state.linkMajelis !== "");
    }

    submitableSimpan() {
        var submitable = false;
        if (this.state.daftarTugasPemeriksaan.length > 0) {

            this.state.daftarTugasPemeriksaan.map(tugas => {
                submitable =
                    (tugas.kantorCabang !== null && tugas.kantorCabang !== "" && tugas.kantorCabang !== undefined) &&
                    (tugas.idQA !== null && tugas.idQA !== "" && tugas.idQA !== undefined) &&
                    (tugas.tanggalMulai !== null && tugas.tanggalMulai !== "" && tugas.tanggalMulai !== undefined) &&
                    (tugas.tanggalSelesai !== null && tugas.tanggalSelesai !== "" && tugas.tanggalSelesai !== undefined);
                return null
            });
        }
        return submitable;
    }

    // Fungsi untuk mengubah state ketika isi dari input diubah
    // Fungsi ini wajib ada jika membuat form
    handleChange(event) {
        const { name, value } = event.target;
        this.setState(
            {
                [name]
                    : value
            }
        );

        switch (name) {
            case "namaRencana":
                this.validateNama(value);
                break;
            case "linkMajelis":
                this.validateLink(value);
                break;
            default:
                break;
        }
    }

    // Fungsi yang akan dijalankan ketika user submit
    // Umumnya akan digunakan untuk memanggil service komunikasi ke backend
    async handleSubmit(event, nama) {
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengirim data ke server");

        event.preventDefault();
        if (this.submitable()) {
            if (nama === "simpan" && this.submitableSimpan()) {
                const rencanaPemeriksaan = {
                    id: this.state.id,
                    namaRencana: this.state.namaRencana,
                    linkMajelis: this.state.linkMajelis,
                    status: 2,
                    daftarTugasPemeriksaan: this.state.daftarTugasPemeriksaan
                }
                RencanaPemeriksaanService.editRencanaPemeriksaan(rencanaPemeriksaan)
                    .then(() => this.setRedirect());

            } else if (nama === "draft") {
                const rencanaPemeriksaan = {
                    id: this.state.id,
                    namaRencana: this.state.namaRencana,
                    linkMajelis: this.state.linkMajelis,
                    status: this.state.status,
                    daftarTugasPemeriksaan: this.state.daftarTugasPemeriksaan
                }
                RencanaPemeriksaanService.editRencanaPemeriksaan(rencanaPemeriksaan)
                    .then(() => this.setRedirect());
            }


        }

        this.props.contentFinishLoading()
    }

    getTitle(i) {
        var title = "Tugas Pemeriksaan " + i
        return title
    }

    fullComponentInside() {
        const forms = [];
        for (let i = 0; i < this.state.daftarTugasPemeriksaan.length; i++) {
            forms.push(
                <SirioForm
                    subtitle={this.getTitle(i + 1)}
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
                    errormessage: this.state.errorNama,
                    value: this.state.namaRencana,
                    placeholder: "Masukan nama rencana"
                }, {
                    label: "Link Pemeriksaan*",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "linkMajelis",
                    errormessage: this.state.errorLink,
                    value: this.state.linkMajelis,
                    placeholder: "https://drive.google.com/"
                }, {
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
            classes="my-3"
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
                    label: "Kantor Cabang",
                    handleChange: this.handleMultipleSelectChange,
                    index: index,
                    required: true,
                    type: "select",
                    name: "kantorCabang",
                    value: this.state.daftarTugasPemeriksaan[index].kantorCabang,
                    optionList: this.state.kantorOptionList
                }, {
                    label: "QA Officer",
                    handleChange: this.handleMultipleSelectChange,
                    index: index,
                    type: "select",
                    name: "idQA",
                    required: true,
                    value: this.state.daftarTugasPemeriksaan[index].idQA,
                    optionList: this.state.employeeOptionList
                }, {
                    label: "Tanggal Mulai",
                    handleChange: this.handleSelectChange,
                    index: index,
                    type: "date",
                    required: true,
                    name: "tanggalMulai",
                    value: this.state.daftarTugasPemeriksaan[index].tanggalMulai
                }, {
                    label: "Tanggal Selesai",
                    handleChange: this.handleSelectChange,
                    index: index,
                    type: "date",
                    validation: this.state.errorTM,
                    min: this.getMin(index),
                    disabled: this.state.daftarTugasPemeriksaan[index].tanggalMulai === "",
                    required: this.state.daftarTugasPemeriksaan[index].tanggalMulai !== "",
                    name: "tanggalSelesai",
                    value: this.state.daftarTugasPemeriksaan[index].tanggalSelesai
                }

            ]
        )
    }

    getMin(index) {
        var dateToAdd = this.state.daftarTugasPemeriksaan[index].tanggalMulai;
        var date = new Date(dateToAdd);
        var after = moment(date).add(1, 'days').format('YYYY[-]MM[-]DD');
        return after;
    }

    submitButton() {
        var tombolJalankan =
            <SirioButton
                purple
                disabled
                classes="mx-1"
            >
                Jalankan
            </SirioButton>;
        if (this.submitableSimpan() && this.submitable()) {
            tombolJalankan =
                <SirioButton
                    purple
                    recommended
                    classes="mx-1"
                    onClick={(event) => this.handleSubmit(event, "simpan")}
                >
                    Jalankan
                </SirioButton>
        }
        var tombolDraft =
            <SirioButton
                purple
                disabled
                classes="mx-1"
            >
                Draft
            </SirioButton>;
        if (this.submitable()) {
            tombolDraft =
                <SirioButton
                    purple
                    recommended
                    classes="mx-1"
                    onClick={(event) => this.handleSubmit(event, "draft")}
                >
                    Draft
                </SirioButton>
        }
        return (
            <div>
                {tombolJalankan}
                {tombolDraft}
                <NavLink to={{
                    pathname: "/rencana-pemeriksaan/detail",
                    state: {
                        id: this.props.location.state.id
                    }
                }}>
                    <SirioButton
                        purple
                    >
                        Batal
                    </SirioButton>
                </NavLink>
            </div>
        )
    }

    deleteItem(array, index) {
        const toReturn = array.slice(0, index).concat(array.slice(index + 1, array.length));
        TugasPemeriksaanService.deleteTugas(array[index])
        return toReturn;
    }


    deleteChildForm(index) {
        const daftarTugasPemeriksaan = this.state.daftarTugasPemeriksaan
        const newdaftarTugasPemeriksaan = this.deleteItem(daftarTugasPemeriksaan, index)

        this.setState(
            {
                daftarTugasPemeriksaan: newdaftarTugasPemeriksaan
            }
        )
    }

    childFooterButton(index) {
        return (
            <div>
                <SirioWarningButton
                    red
                    type="button"
                    modalTitle="Konfirmasi Penghapusan"
                    modalDesc="Apakah anda yakin untuk menghapus tugas pemeriksaan?"
                    onConfirm={() => this.deleteChildForm(index)}
                    customConfirmText="Ya, Hapus"
                    customCancelText="Batal"
                >
                    Hapus
                    </SirioWarningButton>
            </div>
        )
    }

    addForm() {
        const daftarTugasPemeriksaan = this.state.daftarTugasPemeriksaan
        daftarTugasPemeriksaan.push({});

        this.setState(
            {
                daftarTugasPemeriksaan: daftarTugasPemeriksaan
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