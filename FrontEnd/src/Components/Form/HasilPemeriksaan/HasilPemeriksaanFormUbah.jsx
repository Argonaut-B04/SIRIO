import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import HasilPemeriksaanService from '../../../Services/HasilPemeriksaanService';
import RisikoService from '../../../Services/RisikoService';
import { NavLink, Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import RiskLevelService from "../../../Services/RiskLevelService";
import SirioField from "../SirioFormComponent/SirioField";
import TemuanRisikoService from "../../../Services/TemuanRisikoService";
import SirioConfirmButton from "../../Button/ActionButton/SirioConfirmButton";

class HasilPemeriksaanFormTambah extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            idCurrentStatus: "",
            daftarKomponenPemeriksaan: [],
            daftarRisikoKategori1: [],
            daftarRisikoKategori2: [],
            daftarHistoriTemuan: [],
            filterKategori: "",
            kategoriType: "",
            riskLevelOptionList: [],
            riskOptionList: [],
            submitableDraft: false,
            submitable: false,
            redirect: false
        };

        this.renderRisikoKategori12 = this.renderRisikoKategori12.bind(this);
        this.renderHasilPemeriksaan = this.renderHasilPemeriksaan.bind(this);
        this.renderRiskLevelOption = this.renderRiskLevelOption.bind(this);
        this.renderHistoriTemuan = this.renderHistoriTemuan.bind(this);
        this.handleChangeKomponen = this.handleChangeKomponen.bind(this);
        this.handleSelectChangeKomponen = this.handleSelectChangeKomponen.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.handleSelectChangeRisiko = this.handleSelectChangeRisiko.bind(this);
        this.handleMultiFieldChange = this.handleMultiFieldChange.bind(this);
        this.modifyFieldCount = this.modifyFieldCount.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.innerInputDefinition = this.innerInputDefinition.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        this.renderRisikoKategori12();
        this.renderHasilPemeriksaan();
        this.renderRiskLevelOption();
        this.renderHistoriTemuan();
    }

    componentDidUpdate(prevProps, prevState) {
        var submitable = true;
        var submitableDraft = true;

        submitable = this.validateRequired();
        prevState.daftarKomponenPemeriksaan.map((prevKomponen, index) => {
            const validation = this.validateKeteranganSampel();
            submitable = submitable && validation;
            submitableDraft = submitableDraft && validation;
            // if (prevKomponen.keteranganSampel !== this.state.daftarKomponenPemeriksaan[index].keteranganSampel) {
            //     const validation = this.validateKeteranganSampel();
            //     submitable = submitable && validation;
            //     submitableDraft = submitableDraft && validation;
            //     validating = true;
            // }
            //
            // if (prevKomponen.jumlahSampel !== this.state.daftarKomponenPemeriksaan[index].jumlahSampel) {
            //     validating = true;
            // }
            //
            // if (prevKomponen.idRiskLevel !== this.state.daftarKomponenPemeriksaan[index].idRiskLevel) {
            //     validating = true;
            // }

            // prevKomponen.daftarTemuanRisiko.map((prevTemuanRisiko, indexY) => {
            //     if (prevTemuanRisiko.keterangan !== this.state.daftarKomponenPemeriksaan[index].daftarTemuanRisiko[indexY].keterangan) {
            //         validating = true;
            //     }
            // });
            //
            // prevKomponen.daftarRekomendasi.map((prevRekomendasi, indexY) => {
            //     if (prevRekomendasi.keterangan !== this.state.daftarKomponenPemeriksaan[index].daftarRekomendasi[indexY].keterangan) {
            //         validating = true;
            //     }
            // });
        });

        if (this.state.submitable !== submitable) {
            this.setState({
                submitable: submitable
            })
        }

        if (this.state.submitableDraft !== submitableDraft) {
            this.setState({
                submitableDraft: submitableDraft
            })
        }
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/hasil-pemeriksaan",
                state: {
                    editSuccess: true
                }
            }} />
        }
    };

    async renderRiskLevelOption() {
        const response = await RiskLevelService.getAll();

        const riskLevelList = response.data.result.map(riskLevel => {
            return (
                {
                    label: riskLevel.namaLevel,
                    value: riskLevel.idLevel
                }
            )
        });

        this.setState({
            riskLevelOptionList: riskLevelList
        })
    }

    async renderHistoriTemuan() {
        const response = await TemuanRisikoService.getHistoriTemuanRisikoKantorCabang(this.props.location.state.idTugasPemeriksaan);

        const daftarHistoriTemuan = response.data.result;

        this.setState({
            daftarHistoriTemuan: daftarHistoriTemuan
        })
    }

    async renderRisikoKategori12() {
        const response = await RisikoService.getAll();

        const risikoKategori1 = response.data.result
            .filter(risiko => risiko.risikoKategori === 1)
            .map(risiko => {
                return (
                    {
                        label: risiko.namaRisiko,
                        value: risiko.idRisiko
                    }
                )
            });

        const risikoKategori2 = response.data.result
            .filter(risiko => risiko.risikoKategori === 2)
            .map(risiko => {
                return (
                    {
                        label: risiko.namaRisiko,
                        value: risiko.idRisiko
                    }
                )
            });

        this.setState({
            daftarRisikoKategori1: risikoKategori1,
            daftarRisikoKategori2: risikoKategori2
        })
    }

    async renderHasilPemeriksaan() {
        const response = await HasilPemeriksaanService.getHasilPemeriksaan(this.props.location.state.id);

        const idCurrentStatus = response.data.result.idStatus;
        const daftarKomponen = response.data.result.daftarKomponenPemeriksaan
            .map(komponen => {
                return (
                    {
                        id: komponen.id,
                        idRiskLevel: komponen.idRiskLevel,
                        errorIdRiskLevel: "",
                        risiko: komponen.risiko,
                        jumlahSampel: komponen.jumlahSampel,
                        errorJumlahSampel: "",
                        keteranganSampel: komponen.keteranganSampel,
                        errorKeteranganSampel: "",
                        daftarTemuanRisiko: (komponen.daftarTemuanRisikoTerdaftar.length > 0) ?
                            komponen.daftarTemuanRisikoTerdaftar
                            :
                            [{
                                keterangan: ""
                            }],
                        daftarRekomendasi: (komponen.daftarRekomendasiTerdaftar.length > 0) ?
                            komponen.daftarRekomendasiTerdaftar
                            :
                            [{
                                keterangan: ""
                            }]
                    }
                )
            });

        this.setState({
            daftarKomponenPemeriksaan: daftarKomponen,
            idCurrentStatus: idCurrentStatus
        })
    }

    getSOPButton(namaSop, linkSop) {
        return (
            <SirioButton
                purple
                hyperlinkLeft
                onClick={() => window.location.href = linkSop}
            >
                {namaSop}
            </SirioButton>
        )
    }

    getHistoriTemuanButton(idRisiko) {
        const histori = <p>
            {this.state.daftarHistoriTemuan
                .filter(temuan => temuan.idRisiko === idRisiko)
                .map((temuan, index) =>
                    <p className="text-center p-0 m-0">{index+1}. {temuan.keterangan} </p>
                )}
        </p>;

        return (
            <SirioConfirmButton
                purple recommended
                classes="m-1"
                modalTitle= "Riwayat Temuan Risiko"
                modalDesc={histori}
                customConfirmText=" "
                closeOnConfirm
                confirmDisable
            >
                Riwayat Temuan
            </SirioConfirmButton>
        )
    }

    innerInputDefinition(komponen) {
        return (
            [
                {
                    label: "Komponen Risiko",
                    customInput: komponen.risiko.nama
                }, {
                    label: "SOP",
                    customInput: this.getSOPButton(komponen.risiko.namaSop, komponen.risiko.linkSop)
                }, {
                    label: "Jumlah Sampel",
                    handleChange: (event) => this.handleChangeKomponen(event, komponen.id),
                    type: "number",
                    name: "jumlahSampel",
                    min: 0,
                    value: komponen.jumlahSampel,
                    placeholder: "0",
                    validation: komponen.errorJumlahSampel
                }, {
                    label: "Keterangan Sampel",
                    handleChange: (event) => this.handleChangeKomponen(event, komponen.id),
                    type: "textarea",
                    name: "keteranganSampel",
                    value: komponen.keteranganSampel,
                    placeholder: "Keterangan sampel",
                    validation: komponen.errorKeteranganSampel
                }, {
                    label: "Risk Level",
                    handleChange: (name, event) => this.handleSelectChangeKomponen(name, event, komponen.id),
                    type: "select",
                    name: "idRiskLevel",
                    value: komponen.idRiskLevel,
                    optionList: this.state.riskLevelOptionList
                }, {
                    label: "Hasil Temuan",
                    multiple: true,
                    isMultipleObject: true,
                    handleChange: (event, index) => this.handleMultiFieldChange(event, index, komponen.id, komponen.daftarTemuanRisiko, "keterangan"),
                    type: "textArea",
                    name: "daftarTemuanRisiko",
                    value: komponen.daftarTemuanRisiko.map(temuan => temuan.keterangan),
                    modifier: (name, newField, index) => this.modifyFieldCount(name, newField, index, komponen.id, komponen.daftarTemuanRisiko, "keterangan"),
                }, {
                    label: "",
                    customInput: this.getHistoriTemuanButton(komponen.risiko.id)
                }, {
                    label: "",
                    customInput: this.getHistoriTemuanButton(komponen.risiko.id)
                }, {
                    label: "Rekomendasi",
                    multiple: true,
                    isMultipleObject: true,
                    handleChange: (event, index) => this.handleMultiFieldChange(event, index, komponen.id, komponen.daftarRekomendasi, "keterangan"),
                    type: "textArea",
                    name: "daftarRekomendasi",
                    value: komponen.daftarRekomendasi.map(rekomendasi => rekomendasi.keterangan),
                    modifier: (name, newField, index) => this.modifyFieldCount(name, newField, index, komponen.id, komponen.daftarRekomendasi, "keterangan"),
                }
            ]
        )
    }

    outerInputDefinition() {
        if (this.state.daftarKomponenPemeriksaan.length > 0) {
            if (this.state.kategoriType === "1") {
                return (
                    this.state.daftarKomponenPemeriksaan
                        .filter(komponen => komponen.risiko.grantParent === this.state.filterKategori)
                        .map(komponen => {
                            return (
                                {
                                    fullComponent:
                                        <SirioForm
                                            noHeader
                                            isInnerForm
                                            inputDefinition={this.innerInputDefinition(komponen)}
                                        />
                                }
                            )
                        })
                )
            } else if (this.state.kategoriType === "2") {
                return (
                    this.state.daftarKomponenPemeriksaan
                        .filter(komponen => komponen.risiko.parent === this.state.filterKategori)
                        .map(komponen => {
                            return (
                                {
                                    fullComponent:
                                        <SirioForm
                                            noHeader
                                            isInnerForm
                                            inputDefinition={this.innerInputDefinition(komponen)}
                                        />
                                }
                            )
                        })
                )
            } else {
                return (
                    this.state.daftarKomponenPemeriksaan
                        .map(komponen => {
                            return (
                                {
                                    fullComponent:
                                        <SirioForm
                                            noHeader
                                            isInnerForm
                                            inputDefinition={this.innerInputDefinition(komponen)}
                                        />
                                }
                            )
                        })
                )
            }
        } else {
            return (
                [
                    {
                        label: "Loading",
                        customField: <p>Loading...</p>
                    }
                ]
            )
        }
    }

    getBetween() {
        return (
            <>
                <div className="col-md-3 pl-0">
                    <SirioField
                        type="select"
                        handleChange={this.handleSelectChangeRisiko}
                        classes="p-1"
                        name="kategoriType"
                        value={this.state.kategoriType}
                        optionList={
                            [
                                {
                                    label: "Semua",
                                    value: "0"
                                },
                                {
                                    label: "Kategori 1",
                                    value: "1"
                                },
                                {
                                    label: "Kategori 2",
                                    value: "2"
                                }
                            ]
                        }
                    />
                </div>
                <div className="col-md-3 pl-0">
                    <SirioField
                        type="select"
                        handleChange={this.handleSelectChange}
                        classes="p-1"
                        name="filterKategori"
                        value={this.state.filterKategori}
                        optionList={this.state.riskOptionList}
                    />
                </div>
            </>
        )
    }

    modifyFieldCount(name, newField, index, idKomponen, array, objectKey) {
        // const newArray = [];
        // const oriArray = array;
        if (newField.length > array.length) {
            array.push({
                [objectKey]: newField[newField.length - 1]
            })
        } else {
            array.splice(index, 1)
        }
        // for(let i = 0; i < newField.length; i++) {
        //     newArray.push({
        //         [objectKey]: newField[i]
        //     })
        // }
        // oriArray.map((ori, index) => {
        //     if(ori.keterangan === newField[index]) {
        //         newArray.push(ori);
        //     } else {
        //
        //     }
        // });

        this.setState(prevState => ({
            ...prevState,
            daftarKomponenPemeriksaan: prevState.daftarKomponenPemeriksaan.map(komponen => ({
                ...komponen,
                [name]: komponen.id === idKomponen ? array : komponen[name]
            }))
        }))
    }

    handleMultiFieldChange(event, index, idKomponen, array, objectKey) {
        const targetName = event.target.name;
        const targetValue = event.target.value;
        const targetArray = array;
        targetArray[index][objectKey] = targetValue;
        this.setState(prevState => ({
            ...prevState,
            daftarKomponenPemeriksaan: prevState.daftarKomponenPemeriksaan.map(komponen => ({
                ...komponen,
                [targetName]: komponen.id === idKomponen ? targetArray : komponen[targetName]
            }))
        }))
    }

    handleChangeKomponen(event, idKomponen) {
        const { name, value } = event.target;

        this.setState(prevState => ({
            ...prevState,
            daftarKomponenPemeriksaan: prevState.daftarKomponenPemeriksaan.map(komponen => ({
                ...komponen,
                [name]: komponen.id === idKomponen ? value : komponen[name]
            }))
        }))
    }

    handleSelectChangeKomponen(name, event, idKomponen) {
        this.setState(prevState => ({
            ...prevState,
            daftarKomponenPemeriksaan: prevState.daftarKomponenPemeriksaan.map(komponen => ({
                ...komponen,
                [name]: komponen.id === idKomponen ? event.value : komponen[name]
            }))
        }));
    }

    handleSelectChangeRisiko(name, event) {
        const risiko1 = this.state.daftarRisikoKategori1;
        const risiko2 = this.state.daftarRisikoKategori2;
        if (event.value === "1") {
            this.setState(
                {
                    [name]
                        : event.value,
                    riskOptionList: risiko1,
                    filterKategori: risiko1[0].value
                }
            )
        } else if (event.value === "2") {
            this.setState(
                {
                    [name]
                        : event.value,
                    riskOptionList: risiko2,
                    filterKategori: risiko2[0].value
                }
            )
        } else {
            this.setState(
                {
                    [name]
                        : event.value,
                    riskOptionList: [
                        {
                            label: "Semua",
                            value: "-1"
                        }
                    ],
                    filterKategori: "-1"
                }
            )
        }
    }

    handleSelectChange(name, event) {
        this.setState(
            {
                [name]
                    : event.value,
            }
        )
    }

    handleSubmit(event, status) {
        event.preventDefault();

        const hasilPemeriksaan = {
            id: this.props.location.state.id,
            idStatus: status,
            daftarKomponenPemeriksaan: this.state.daftarKomponenPemeriksaan.map(komponen => {
                const daftarTemuan = [];
                const daftarTemuanTerdaftar = [];
                const daftarRekomendasi = [];
                const daftarRekomendasiTerdaftar = [];
                komponen.daftarTemuanRisiko.map(temuan => {
                    if (temuan.id != null) {
                        daftarTemuanTerdaftar.push(temuan)
                    } else {
                        daftarTemuan.push(temuan)
                    } return null
                });
                komponen.daftarRekomendasi.map(rekomendasi => {
                    if (rekomendasi.id != null) {
                        daftarRekomendasiTerdaftar.push(rekomendasi)
                    } else {
                        daftarRekomendasi.push(rekomendasi)
                    } return null
                });
                return (
                    {
                        id: komponen.id,
                        idRiskLevel: komponen.idRiskLevel,
                        risiko: komponen.risiko,
                        jumlahSampel: komponen.jumlahSampel,
                        keteranganSampel: komponen.keteranganSampel,
                        daftarTemuanRisiko: daftarTemuan,
                        daftarRekomendasi: daftarRekomendasi,
                        daftarTemuanRisikoTerdaftar: daftarTemuanTerdaftar,
                        daftarRekomendasiTerdaftar: daftarRekomendasiTerdaftar
                    }
                )
            })
        };
        if ((status === 1 && this.state.submitableDraft) || ((status === 2 || status === 3) && this.state.submitable)) {
            HasilPemeriksaanService.editHasilPemeriksaan(hasilPemeriksaan)
                .then(() => this.setRedirect());
        }
    }

    validateRequired() {
        var submitable = true;
        this.state.daftarKomponenPemeriksaan.map(komponen => {
            submitable = submitable &&
                (komponen.jumlahSampel !== null && komponen.jumlahSampel !== "") &&
                (komponen.keteranganSampel !== null && komponen.keteranganSampel !== "") &&
                (komponen.idRiskLevel !== null && komponen.idRiskLevel !== "");
            return null
        });
        return submitable;
    }

    validateKeteranganSampel() {
        var submitable = true;
        this.state.daftarKomponenPemeriksaan.map((komponen, index) => {
            const fokusKeteranganSampel = komponen.keteranganSampel;
            var errorKeteranganSampel;
            var letter = /.*[a-zA-Z].*/;

            if (!fokusKeteranganSampel.match(letter) && fokusKeteranganSampel !== "") {
                submitable = false;
                errorKeteranganSampel = "Ketarangan perlu mengandung huruf";
            }
            if (komponen.errorKeteranganSampel !== errorKeteranganSampel) {
                this.setState(prevState => ({
                    ...prevState,
                    daftarKomponenPemeriksaan: prevState.daftarKomponenPemeriksaan.map(komponenState => ({
                        ...komponenState,
                        errorKeteranganSampel: komponenState.id === komponen.id ? errorKeteranganSampel : komponenState.errorKeteranganSampel
                    }))
                }))
            } return null
        });
        return submitable;
    }

    submitButton() {
        return (
            <div>
                <SirioButton purple
                    recommended={this.state.submitable}
                    disabled={!this.state.submitable}
                    classes="mx-1"
                    onClick={(event) => this.handleSubmit(event, 2)}>
                    Simpan
                </SirioButton>
                <SirioButton purple
                    recommended={this.state.idCurrentStatus === 1 ? this.state.submitableDraft : this.state.submitable}
                    disabled={this.state.idCurrentStatus === 1 ? !this.state.submitableDraft : !this.state.submitable}
                    classes="mx-1"
                    onClick={(event) => this.handleSubmit(event, this.state.idCurrentStatus)}>
                    Draft
                </SirioButton>
                <NavLink to={{
                    pathname: "/hasil-pemeriksaan/detail",
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

    // Fungsi render SirioForm
    render() {
        return (
            <>
                {this.renderRedirect()}
                <SirioForm
                    title="Form Ubah Hasil Pemeriksaan"
                    betweenTitleSubtitle={this.getBetween()}
                    inputDefinition={this.outerInputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />
            </>
        );
    }
}

export default withRouter(HasilPemeriksaanFormTambah);